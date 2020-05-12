package edache.joe.user.service;

import edache.joe.security.JwtTokenProvider;
import edache.joe.user.AuthProvider;
import edache.joe.user.User;
import edache.joe.user.UserRepository;
import edache.joe.user.UserRole;
import edache.joe.user.controller.payload.AuthenticationResponse;
import edache.joe.user.controller.payload.LoginPayload;
import edache.joe.user.controller.payload.RegistrationPayload;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserService {

    final private UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;
    final private JwtTokenProvider jwtTokenProvider;
    final private AuthenticationManager authenticationManager;

    public AuthenticationResponse loginUserAuthenticationManger(@NonNull LoginPayload payload) {
        val authUser =
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword()));
        return new AuthenticationResponse(HttpStatus.OK, jwtTokenProvider.signJwt(authUser));
    }

    @Override
    public AuthenticationResponse loginUser(@NonNull LoginPayload payload) {
        val username = payload.getUsername();
        val user = userRepository.findByEmailOrPhoneNumber(username, username);

        if (user != null &&
                passwordEncoder.matches(payload.getPassword(), user.getPassword())) {
            return new AuthenticationResponse(HttpStatus.OK, jwtTokenProvider.signJwt(user));
        }
        return new AuthenticationResponse(HttpStatus.BAD_REQUEST);
    }


    @Override
    public AuthenticationResponse registerUser(@NonNull RegistrationPayload payload) {
        if (!userRepository
                .existsByEmailAndEmailIsNotNullOrPhoneNumberAndPhoneNumberIsNotNull(
                        payload.getEmail(), payload.getPhoneNumber())) {
            val uuid = UUID.randomUUID().toString();
            val user = payload.createUser();
            user.setId(uuid);
            user.setProviderId(uuid);
            user.setProvider(AuthProvider.LOCAL);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userRepository.save(user);

            return new AuthenticationResponse(HttpStatus.CREATED, jwtTokenProvider.signJwt(user));
        }
        return new AuthenticationResponse(HttpStatus.BAD_REQUEST);
    }
}
