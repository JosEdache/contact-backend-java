package edache.joe.user.service;

import edache.joe.security.JwtTokenProvider;
import edache.joe.security.oauth2.AuthProvider;
import edache.joe.user.UserRepository;
import edache.joe.user.controller.payload.AuthenticationResponse;
import edache.joe.user.controller.payload.LoginPayload;
import edache.joe.user.controller.payload.RegistrationPayload;
import edache.joe.execption.AuthenticationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
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
       try {
           val authUser =
                   authenticationManager
                           .authenticate(new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword()));
           return AuthenticationResponse.of(jwtTokenProvider.signJwt(authUser));
       }
       catch (Exception ex) {
           throw new AuthenticationException();
       }
    }

    @Override
    public AuthenticationResponse loginUser(@NonNull LoginPayload payload) {
        val username = payload.getUsername();
        val user = userRepository.findByEmailOrPhoneNumber(username, username);

        if (user == null ||
                !passwordEncoder.matches(payload.getPassword(), user.getPassword())) {
            throw new AuthenticationException();
        }
        return AuthenticationResponse.of(jwtTokenProvider.signJwt(user));
    }


    @Override
    public AuthenticationResponse registerUser(@NonNull RegistrationPayload payload) {
        if (userRepository
                .existsByEmailAndEmailIsNotNullOrPhoneNumberAndPhoneNumberIsNotNull(
                        payload.getEmail(), payload.getPhoneNumber())) {
            throw new AuthenticationException();
        }
        val uuid = UUID.randomUUID().toString();
        val user = payload.createUser();
        user.setId(uuid);
        user.setProviderId(uuid);
        user.setProvider(AuthProvider.local);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        return AuthenticationResponse.of( jwtTokenProvider.signJwt(user));
    }
}
