package edache.joe.security;

import edache.joe.security.user.CustomUserPrincipal;
import edache.joe.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

@RequiredArgsConstructor(staticName = "of")
public class JwtTokenProvider {
    public static final String BEARER = "Bearer ";
    private final String jwtIssuer;
    private final String jwtSecret;
    @Getter(lazy = true)
    private final SecretKey signatureKey = generateSecretKey();

    private SecretKey generateSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    private Date getExpiration() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    public String signJwt(final Authentication authUser) {
        final CustomUserPrincipal principal = (CustomUserPrincipal) authUser.getPrincipal();
        return Jwts.builder()
                .setIssuer(jwtIssuer)
                .setSubject(principal.getId())
                .setAudience(jwtIssuer)
                .setIssuedAt(new Date())
                .claim("role", principal.getRole())
                .signWith(getSignatureKey())
                .compact();
    }

    public String signJwt(final User user) {
        return Jwts.builder()
                .setIssuer(jwtIssuer)
                .setAudience(jwtIssuer)
                .setSubject(user.getId().toString())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(getExpiration())
                .signWith(generateSecretKey())
                .compact();
    }

    public Jws<Claims> parseJws(final String jws) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(jws);
    }

    public String stripBearerFromToken(String token) {
        return token.replace(BEARER, "");
    }

    public String addBearerToJws(String jws) {
        return BEARER.concat(jws);
    }

}
