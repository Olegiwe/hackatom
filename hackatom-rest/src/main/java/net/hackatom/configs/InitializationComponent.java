package net.hackatom.configs;


import net.hackatom.services.InitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class InitializationComponent implements ApplicationRunner {

    @Autowired
    private InitializationService initializationService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.initializationService.init();
    }
}
