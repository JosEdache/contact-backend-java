package edache.joe.security;

import edache.joe.contact.ContactController;
import edache.joe.security.filter.CustomUsernamePasswordAuthenticationFilter;
import edache.joe.security.oauth2.OAuth2ResourceServerAuthenticationManager;
import edache.joe.user.controller.UserController;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${oauth2.resource-server.google.issuer-uri}")
    private String googleIssuerUri;
    @Value("${oauth2.resource-server.facebook.issuer-uri}")
    private String facebookIssuerUri;
    @Value("${app.jwt.issuer-uri}")
    private String appIssuerUri;
    @Value("${app.jwt.secret}")
    private String appJwtSecret;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .addFilter(new CustomUsernamePasswordAuthenticationFilter(authenticationManager(), jwtService()))
//                .authorizeRequests()
//                .mvcMatchers(
//                        UserController.BASE_URL.concat(UserController.SIGN_UP_URL)).permitAll()
//                .mvcMatchers(ContactController.BASE_URL.concat("/**"))
//                .hasAnyAuthority("SCOPE_USER")
//                .anyRequest().authenticated()
//                .and()
//                .oauth2ResourceServer()
//                .authenticationManagerResolver(new JwtIssuerAuthenticationManagerResolver(resourceServerJwtResolver()))
//                // TODO set AccessDeniedHandler for resource server
//                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public AuthenticationManager authenticationManageBean() throws Exception {
        return  super.authenticationManagerBean();
    }

    @Bean
    public JwtTokenProvider jwtService() {
        return JwtTokenProvider.of(appIssuerUri, appJwtSecret);
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public AffirmativeBased accessDecisionManager() {
        List<AccessDecisionVoter<?>> voters = new ArrayList<>();
        voters.add(new RoleVoter());
        voters.add(new WebExpressionVoter());
        return new AffirmativeBased(voters);
    }

    public AuthenticationManagerResolver<String> resourceServerJwtResolver() {
        val manager = OAuth2ResourceServerAuthenticationManager.of();
        manager.addManager(googleIssuerUri);
//        manager.addManager(facebookIssuerUri);
        manager.addManager(appIssuerUri, jwtService().getSignatureKey());
        return manager::getManager;
    }

}
