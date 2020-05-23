package edache.joe.security.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import edache.joe.AppConfig;
import edache.joe.security.JwtTokenProvider;
import edache.joe.user.controller.payload.AuthenticationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.val;


final public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    final static String PROCESSING_URL = AppConfig.BASE_URL.concat("/login");
    final JwtTokenProvider jwtTokenProvider;
    final ObjectMapper mapper = new ObjectMapper();

    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                      JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        setAuthenticationManager(authenticationManager);
        setFilterProcessesUrl(PROCESSING_URL);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        val authResponse = AuthenticationResponse.of(jwtTokenProvider.signJwt(authResult));
        mapper.writerWithDefaultPrettyPrinter().writeValue(response.getWriter(), authResponse);
//        response.setHeader(HttpHeaders.AUTHORIZATION, jwtTokenProvider.addBearerToJws(token));
    }
}
