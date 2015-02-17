package com.tinesoft.droidlinguist.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import com.tinesoft.droidlinguist.server.config.Constants;

/**
 * This is a helper Java class that provides an alternative to creating a web.xml.
 */
public class DroidLinguistApplicationWebXml extends SpringBootServletInitializer {

    private final Logger log = LoggerFactory.getLogger(DroidLinguistApplicationWebXml.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.profiles(addDefaultProfile())
            .sources(DroidLinguistApplication.class);
    }

    /**
     * Set a default profile if it has not been set.
     * <p>
     * Please use -Dspring.profiles.active=dev
     * </p>
     */
    private String addDefaultProfile() {
        String profile = System.getProperty("spring.profiles.active");
        if (profile != null) {
            log.info("Running with Spring profile(s) : {}", profile);
            return profile;
        }

        log.warn("No Spring profile configured, running with default configuration");
        return Constants.SPRING_PROFILE_DEVELOPMENT;
    }
}
