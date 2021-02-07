package net.hackatom.readers;


import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.querydsl.core.group.GroupBy.groupBy;

@Component
public class QueryDslUtils {

    @SneakyThrows
    public <T> List<T> cascadeDtoSelect(Class<T> dtoClass, JPAQuery<?> query) {
        List<EntityPath<?>> entityJoins = query.getMetadata().getJoins().stream()
                .map(join -> (EntityPath<?>) join.getTarget())
                .collect(Collectors.toList());
        EntityPath<?> rootEntity = entityJoins.remove(0);
        Expression<?> id = (Expression<?>) rootEntity.getClass().getField("id").get(rootEntity);
        return query.transform(groupBy(id).list(Projections.bean(dtoClass, getRecursiveFieldBindings(dtoClass, rootEntity, entityJoins))));
    }

    // Map<String, Expression<?>>
    @SneakyThrows
    public <T> Map<String, Expression<?>> getRecursiveFieldBindings(Class<T> dtoClass, EntityPath<?> entity,
                                                                    List<EntityPath<?>> entityJoins) {
        Map<String, Expression<?>> bindingsMap = new HashMap<>();

        Map<String, List<EntityPath<?>>> joinsMap = entityJoins.stream()
                .collect(Collectors.toMap(e -> e.getType().getSimpleName().toLowerCase(Locale.ROOT),
                        e -> Arrays.asList(e), (e1, e2) -> Stream.concat(e1.stream(), e2.stream()).collect(Collectors.toList())));

        Map<String, Expression<?>> entityFieldsMap = new HashMap<>();
        for (Field field : entity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            // if NumberPath, StringPath, etc
            if (field.get(entity) instanceof Expression<?> && !(field.get(entity) instanceof EntityPath<?>)) {
                entityFieldsMap.put(field.getName(), (Expression<?>) field.get(entity));
            }
        }

        Arrays.asList(dtoClass.getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            if (entityFieldsMap.containsKey(field.getName())) {
                if (!field.getType().isAssignableFrom(List.class))
                    bindingsMap.put(field.getName(), entityFieldsMap.get(field.getName()));
            } else {
                String typeName = field.getType().getSimpleName().toLowerCase(Locale.ROOT);
                if (typeName.endsWith("dto")) {
                    typeName = typeName.substring(0, typeName.lastIndexOf("dto"));
                }
                if (joinsMap.containsKey(typeName)) {
                    List<EntityPath<?>> joins = joinsMap.get(typeName);
                    for (EntityPath<?> j : joins) {
                        System.out.println(j.getMetadata().getName());
                    }
                    EntityPath<?> join = joins.stream()
                            .filter(j -> j.getMetadata().getName().equals(field.getName()))
                            .findFirst()
                            .orElse(null);
//                    List<EntityPath<?>> newJoins = entityJoins.stream()
//                            .filter(join -> !join.getMetadata().getName().equals(entity.getMetadata().getName()))
//                            .collect(Collectors.toList());
//                    entityJoins.remove(0);
                    if (join != null) {
                        bindingsMap.put(field.getName(),
                                Projections.bean(field.getType(),
                                        getRecursiveFieldBindings(field.getType(), /*joinsMap.get(typeName)*/join, entityJoins)));
                    }
                }
            }
        });
        return bindingsMap;
    }
}
