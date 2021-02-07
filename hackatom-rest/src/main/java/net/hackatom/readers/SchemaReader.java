package net.hackatom.readers;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.hackatom.Dto.SchemaDto;
import net.hackatom.entity.QAttachment;
import net.hackatom.entity.QRegion;
import net.hackatom.entity.QSchema;
import net.hackatom.entity.QUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository
@Transactional(readOnly = true)
public class SchemaReader {

    private static final QSchema schema = QSchema.schema;
    private static final QRegion region = QRegion.region;
    private static final QUnit unit = QUnit.unit;
    private static final QAttachment attachment = QAttachment.attachment;
    private static final QAttachment anotherAttachment = new QAttachment("anotherAttachment");

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private QueryDslUtils queryDslUtils;

    @Autowired
    private ReaderHelper readerHelper;

    private JPAQueryFactory queryFactory = new JPAQueryFactory(() -> entityManager);


//    public SchemaDto getSchema(Long id) {
//         return queryFactory.from(schema)
//                 .leftJoin(attachment).on(attachment.schemaId.eq(schema.id))
//                .where(schema.id.eq(id))
//    }

    public SchemaDto getSchema(Long id) {

        JPAQuery query = queryFactory
                .from(schema)
                .leftJoin(attachment).on(attachment.schemaId.eq(schema.id))
                .leftJoin(anotherAttachment).on(anotherAttachment.schemaId.eq(schema.anotherAttachmentId))
                .leftJoin(unit).on(attachment.unitId.eq(unit.id))
                .where(schema.id.eq(id));
        return this.queryDslUtils.cascadeDtoSelect(SchemaDto.class, query).get(0);
        // return this.readerHelper.selectDto(SchemaDto.class, query).get(0);
    }


}
