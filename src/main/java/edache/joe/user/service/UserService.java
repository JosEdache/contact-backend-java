package edache.joe.user.service;

import edache.joe.user.controller.payload.AuthenticationResponse;
import edache.joe.user.controller.payload.LoginPayload;
import edache.joe.user.controller.payload.RegistrationPayload;
import lombok.NonNull;

public interface UserService {
    AuthenticationResponse loginUser(@NonNull LoginPayload payload);
    AuthenticationResponse registerUser(@NonNull RegistrationPayload payload);
}
