package net.hackatom.readers;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.hackatom.Dto.DefectDto;
import net.hackatom.Dto.Page;
import net.hackatom.Dto.QueryModifier;
import net.hackatom.Dto.UnitDto;
import net.hackatom.entity.QAttachment;
import net.hackatom.entity.QDefect;
import net.hackatom.entity.QSystem;
import net.hackatom.entity.QUnit;
import net.hackatom.repositories.AttachmentRepository;
import net.hackatom.repositories.DefectRepository;
import net.hackatom.repositories.SystemRepository;
import net.hackatom.repositories.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class GlobalReader {

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    DefectRepository defectRepository;

    @Autowired
    SystemRepository systemRepository;

    @Autowired
    UnitRepository unitRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    ReaderHelper readerHelper;

    private static final QUnit unit = QUnit.unit;
    private static final QAttachment attachment = QAttachment.attachment;
    private static final QSystem system = QSystem.system;
    private static final QDefect defect = QDefect.defect;

    public Page<?> getUnits(QueryModifier modifier) {
        return readerHelper.selectPage(UnitDto.class, getQuery()
                .from(unit).leftJoin(system).on(unit.systemId.eq(system.id)), modifier);
    }

    public Page<?> getDefects(QueryModifier modifier) {
        return readerHelper.selectPage(DefectDto.class, getQuery()
        .from(defect).leftJoin(unit).on(defect.unitId.eq(unit.id)), modifier);
    }

    private JPAQuery<?> getQuery() {
        return new JPAQueryFactory(entityManager).query();
    }

}
