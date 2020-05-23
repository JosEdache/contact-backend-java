package edache.joe.user.controller.payload;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class AuthenticationResponse {
    final public String token;
}
