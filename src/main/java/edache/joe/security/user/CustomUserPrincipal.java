package edache.joe.security.user;

import edache.joe.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@AllArgsConstructor()
public class CustomUserPrincipal implements UserDetails {
    private String id, username, password, role;
    private boolean enabled;
    private Collection<? extends GrantedAuthority> authorities ;

    static CustomUserPrincipal from(User user) {
        return new CustomUserPrincipal(
                user.getId().toString(),
                user.getName(),
                user.getPassword(),
                user.getRole().name(),
                user.isEnabled(),
                AuthorityUtils.createAuthorityList(user.getRole().name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled();
    }

}
