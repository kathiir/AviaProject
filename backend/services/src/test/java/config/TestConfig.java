package config;


import org.springframework.context.annotation.*;
import org.springframework.test.context.ContextConfiguration;
import service.ServicesConfig;

@ComponentScan(basePackageClasses = ServicesConfig.class)
@PropertySource("classpath:application.properties")
@ContextConfiguration(classes = TestConfig.class)
public class TestConfig {

}
