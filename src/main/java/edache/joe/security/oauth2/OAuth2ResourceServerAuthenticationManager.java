package edache.joe.security.oauth2;

import lombok.NoArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;

import javax.crypto.SecretKey;
import java.util.HashMap;

@NoArgsConstructor(staticName = "of")
public class OAuth2ResourceServerAuthenticationManager {

    private final HashMap<String, AuthenticationManager> authenticationManagers = new HashMap<>();

    public JwtAuthenticationProvider createManager(JwtDecoder decoder) {
        return new JwtAuthenticationProvider(decoder);
    }

    public void addManager(String issuerUri) {
        val manager = createManager(JwtDecoders.fromIssuerLocation(issuerUri));
        authenticationManagers.put(issuerUri, manager::authenticate);
    }

    public void addManager(String issuerUri, SecretKey key) {
        val manager = createManager(
                NimbusJwtDecoder
                        .withSecretKey(key)
                        .macAlgorithm(MacAlgorithm.HS512)
                        .build());
        authenticationManagers.put(issuerUri, manager::authenticate);
    }

    public AuthenticationManager getManager(String issuerUri) {
        return authenticationManagers.get(issuerUri);
    }

}
