package com.phuocnguyen.app.sivaoswsc.Config.ApplicationStartupRunnerConfigurer;

import com.sivaos.Configurer.SIVAJDBCConnectAutomation.SIVAJDBCConnectConfigurer;
import com.sivaos.Utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(-1)
public class ApplicationStartupRunnerConfigurer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartupRunnerConfigurer.class);


    private final SIVAJDBCConnectConfigurer connectConfigurer;

    @Autowired
    public ApplicationStartupRunnerConfigurer(SIVAJDBCConnectConfigurer connectConfigurer) {
        this.connectConfigurer = connectConfigurer;
    }

    @Override
    public void run(String... args) {
        logger.info("config database schema current: {}", LoggerUtils.snagAsLogJson(connectConfigurer.toString()));
        logger.info("SivaOs WSC starting...auto generate....");
    }
}
