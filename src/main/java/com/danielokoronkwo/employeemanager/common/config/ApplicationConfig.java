package com.danielokoronkwo.employeemanager.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({ DatabaseConfig.class, JwtConfig.class })
public class ApplicationConfig {

}
