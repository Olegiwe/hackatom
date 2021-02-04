package net.hackatom.repositories;

import net.hackatom.entity.Defect;
import org.springframework.data.repository.CrudRepository;

public interface DefectRepository extends CrudRepository<Defect, Long> {
}
