package net.hackatom.readers;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.hackatom.repositories.AttachmentRepository;
import net.hackatom.repositories.DefectRepository;
import net.hackatom.repositories.SystemRepository;
import net.hackatom.repositories.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

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





    private JPAQuery<?> getQuery() {
        return new JPAQueryFactory(entityManager).query();
    }

}
