package service;

import avia.DbConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAutoConfiguration
@EnableScheduling
@Import(DbConfig.class)
@ComponentScan(basePackageClasses = ServicesConfig.class)
public class ServicesConfig {
}
