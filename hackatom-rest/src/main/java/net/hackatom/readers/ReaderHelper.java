package net.hackatom.readers;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.SneakyThrows;
import net.hackatom.Dto.Page;
import net.hackatom.Dto.PageFilter;
import net.hackatom.Dto.QueryModifier;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Component
public class ReaderHelper {

    public <T> List<T> selectDto(Class<T> clazz, JPAQuery<?> query) {

        List<EntityPath<?>> joins = query.getMetadata().getJoins()
                .stream().map(e -> (EntityPath<?>) e.getTarget()).collect(Collectors.toList());

        EntityPath<?> root = joins.get(0);

        Expression<?> id;
        try {
            id = (Expression<?>) root.getClass().getField("id").get(root);
        } catch (Exception ignored) {
            throw new RuntimeException("Not found id field");
        }
        return query.transform(groupBy(id)
                .list(Projections.bean(clazz, getBindings(clazz, root, joins))));
    }

    public <T> List<T> selectDto(Class<T> clazz, JPAQuery<?> query, QueryModifier modifier) {
        query = setQueryModifier(query, modifier);
        return selectDto(clazz, query);
    }

    @SneakyThrows
    public <T> Page<T> selectPage(Class<T> clazz, JPAQuery<?> query, QueryModifier modifier) {
        Page<T> page = new Page<>();
        query = setQueryModifier(query, modifier);
        List<EntityPath<?>> joins = query.getMetadata().getJoins()
                .stream().map(e -> (EntityPath<?>) e.getTarget()).collect(Collectors.toList());
        EntityPath<?> root = joins.get(0);
        Expression<?> id = (Expression<?>) root.getClass().getField("id").get(root);

        page.setTotal(query.fetchCount());
        page.setPayload(query.transform(groupBy(id)
                .list(Projections.bean(clazz, getBindings(clazz, root, joins)))));
        return page;
    }

    public JPAQuery<?> setQueryModifier(JPAQuery<?> query, QueryModifier modifier) {
        if (modifier != null) {
            if (modifier.getLimit() != null && modifier.getOffset() != null) {
                paging(query, modifier);
            }
            if (modifier.getSortField() != null) {
                setSorting(query, modifier);
            }
            if (modifier.getFilters() != null && modifier.getFilters().size() > 0) {
                setFilter(query, modifier.getFilters());
            }
        }
        return query;
    }

    private void setFilter(JPAQuery<?> query, List<PageFilter> filters) {
        EntityPath<?> root = (EntityPath<?>) query.getMetadata().getJoins().get(0).getTarget();
        filters = filters.stream().filter(e -> e.getModeValMap() != null && e.getModeValMap().size() > 0).collect(Collectors.toList());
        try {
            for (PageFilter filter : filters) {
                Expression<?> field = (Expression<?>) root.getClass()
                        .getField(filter.getFieldName()).get(root);
                if (field.getType().getSimpleName().toLowerCase().contains("string"))
                    setStringFilter(query, field, filter.getModeValMap());
                if (field.getType().getSimpleName().toLowerCase().contains("date"))
                    setDateFilter(query, field, filter.getModeValMap());
            }

        } catch (
                Exception ex) {
            throw new RuntimeException("Problem with filtering");
        }

    }

    private void setStringFilter(JPAQuery<?> query, Expression<?> field, Map<String, String> modeValMap) {
        modeValMap.forEach((matchMode, value) -> {
            try {
                switch (matchMode) {
                    case "contains": {
                        query.where(getPredicate(field, "containsIgnoreCase",
                                new Class[]{field.getType()}, value));
                        break;
                    }
                    case "startsWith": {
                        query.where(getPredicate(field, "startsWithIgnoreCase",
                                new Class[]{field.getType()}, value));
                        break;
                    }
                    case "equals": {
                        query.where(getPredicate(field, "equalsIgnoreCase",
                                new Class[]{field.getType()}, value));
                        break;
                    }
                }
            } catch (IllegalAccessException
                    | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }

    private void setDateFilter(JPAQuery<?> query, Expression<?> field, Map<String, String> modeValMap) {
        modeValMap.forEach((matchMode, value) -> {
            LocalDate date = LocalDate.parse(value, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            matchMode = matchMode.equalsIgnoreCase("startsWith") ? "after" : matchMode;
            try {
                switch (matchMode) {
                    case ("after"): {
                        Class<?>[] parameterTypes = new Class[]{Comparable.class};
                        query.where(getPredicate(field, "after", parameterTypes, date)
                                .or(getPredicate(field, "eq", new Class[]{Object.class}, date)));
                        break;
                    }
                    case ("before"): {
                        Class<?>[] parameterTypes = new Class[]{Comparable.class};
                        query.where(getPredicate(field, "before", parameterTypes, date)
                                .or(getPredicate(field, "eq", new Class[]{Object.class}, date)));
                        break;
                    }
                    case ("is"): {
                        Class<?>[] parameterTypes = new Class[]{Object.class};
                        query.where(getPredicate(field, "eq",
                                parameterTypes, date));
                        break;
                    }
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }

        });
    }

    private BooleanExpression getPredicate(Expression<?> field,
                                           String methodName, Class<?>[] parameterType, Object... values)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return (BooleanExpression) field.getClass()
                .getMethod(methodName, parameterType)
                .invoke(field, values);
    }

    private void setSorting(JPAQuery<?> query, QueryModifier modifier) {
        try {
            EntityPath<?> root = (EntityPath<?>) query.getMetadata().getJoins().get(0).getTarget();

//            Field positionNumber = root.getClass().getField(modifier.getSortField());
//            var sort = QIssue.issue.positionNumber.substring(0, QIssue.issue.positionNumber.length().add(-2)).castToNum(Integer.class);
//            query.orderBy(modifier.getSortOrder() < 0 ? sort.desc() : sort.asc());

            Expression<?> sortField = (Expression<?>) root.getClass()
                    .getField(modifier.getSortField().equals("positionNumber") ?
                            "id" : modifier.getSortField())
                    .get(root);

            query.orderBy((OrderSpecifier<?>) sortField.getClass()
                    .getMethod(modifier.getSortOrder() < 0 ? "desc" : "asc").invoke(sortField));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void paging(JPAQuery<?> query, QueryModifier modifier) {
        query.offset(modifier.getOffset()).limit(modifier.getLimit());
    }

    @SneakyThrows
    private <T> Map<String, Expression<?>> getBindings(Class<T> clazz, EntityPath<?> target,
                                                       List<EntityPath<?>> joins) {
        var bindings = new HashMap<String, Expression<?>>();

        var mapFields = getFieldMap(target);
        var mapJoins = joins
                .stream().collect(Collectors.toMap(e -> e.getType()
                        .getSimpleName().toLowerCase(Locale.ROOT), e -> e, (e1, e2) -> e1));
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (mapFields.containsKey(field.getName()) && !(mapFields.get(field.getName()) instanceof EntityPath)) {
                if (field.getType().isAssignableFrom(List.class)) {
                    try {
                        Class<?> elClass = Class.forName(getClassName(field.getGenericType().getTypeName()));
//                        var className = Introspector
//                                .decapitalize(elClass.getSimpleName())
//                                .replace("Dto", "");
                        var className = elClass.getSimpleName()
                                .toLowerCase().replace("dto", "");
                        if (mapJoins.containsKey(className))
                            bindings.put(field.getName(), list(Projections.bean(elClass,
                                    getBindings(elClass, mapJoins.get(className), joins))));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    bindings.put(field.getName(), mapFields.get(field.getName()));
            } else {
                String name = field.getType().getSimpleName().toLowerCase().replace("dto", "");
                if (mapJoins.containsKey(name)) {
                    joins.remove(0);
                    bindings.put(field.getName(),
                            Projections.bean(field.getType(),
                                    getBindings(Class.forName(field.getType().getName()),
                                            mapJoins.get(name), joins)));
                }

            }
        }
        return bindings;
    }

    private Map<String, Expression<?>> getFieldMap(EntityPath<?> entity) {
        Map<String, Expression<?>> map = new HashMap<>();
        try {
            for (Field field : entity.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (!field.getName().equalsIgnoreCase(entity.getType().getName())
                        && field.get(entity) instanceof Expression<?> && !(field.get(entity) instanceof EntityPath))
                    map.put(field.getName(), (Expression<?>) field.get(entity));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    private String getClassName(String str) {
        return StringUtils.substringBetween(str, "<", ">");
    }

}
