package edache.joe;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("edache.joe")
@EnableWebMvc
@PropertySource("classpath:application.properties")
public class AppConfig implements WebMvcConfigurer {
    public static final String BASE_URL = "/api";

    @Autowired
    Environment env;

    @Bean
    static public PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        val pc = new PropertySourcesPlaceholderConfigurer();
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
        String[] allowedOrigins = {"http://localhost:3000", "http://localhost:3000", "https://kind-engelbart-ec343a.netlify.com"};
        String[] allowedMethods = {"GET", "POST", "PUT", "DELETE"};

        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods(allowedMethods)
                .allowCredentials(true);
    }

}
