package net.hackatom.services;

import net.hackatom.entity.System;
import net.hackatom.entity.Unit;
import net.hackatom.repositories.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@Transactional
public class InitializationService {

    private static final String initSystemName = "AUTOSAVE_SYSTEM";

    @Autowired
    private SystemRepository systemRepository;


    public void init() {
        java.lang.System.out.println("Init service invoked!");
        if (systemRepository.findAllByName(initSystemName).isEmpty()) {
            systemRepository.save(new System(123L, "AUTOSAVE_SYSTEM", 4, new ArrayList<Unit>()));
        }

    }

}
