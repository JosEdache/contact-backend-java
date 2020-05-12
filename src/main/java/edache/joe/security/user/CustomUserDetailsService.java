package edache.joe.security.user;

import edache.joe.user.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.val;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @NonNull UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        val user = userRepository.findByEmailOrPhoneNumber(username, username);
        if (user == null) {
            throw new UsernameNotFoundException("No user");
        }

        return CustomUserPrincipal.from(user);
    }
}
