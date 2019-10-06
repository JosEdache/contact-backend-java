package com.project.joe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@ComponentScan
@EnableWebMvc
@PropertySource("classpath:application.properties")
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    Environment env;

    @Bean
    static public PropertyPlaceholderConfigurer placeholderConfigurer() {
        PropertyPlaceholderConfigurer pc = new PropertyPlaceholderConfigurer();
        pc.setLocations(new ClassPathResource("application.properties"));
        return pc;
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setBasename("error-codes");
        return ms;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] allowedOrigins = {"http://localhost:3000"};
        String[] allowedMethods = {"GET", "POST", "PUT", "DELETE"};
        /*List<String> profiles = Arrays.asList(env.getActiveProfiles());
        if (profiles.contains("production")) {
            allowedOrigin = "";
        } else {
            allowedOrigin = "http://localhost:3000";
        }*/

        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods(allowedMethods);
    }

}
