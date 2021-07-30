package us.careydevelopment.util.ip.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan("us.careydevelopment.util.ip")
public class IpApiConfig {

    public static void init() {
        ApplicationContext context = new AnnotationConfigApplicationContext(IpApiConfig.class);
    }
    
    
    @Bean 
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
