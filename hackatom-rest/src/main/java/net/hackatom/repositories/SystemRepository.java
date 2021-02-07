package net.hackatom.repositories;

import net.hackatom.entity.System;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SystemRepository extends JpaRepository<System, Long> {
    List<System> findAllByName(String name);
}
