package edache.joe.user.controller;


import edache.joe.user.controller.payload.AuthenticationResponse;
import edache.joe.user.controller.payload.LoginPayload;
import edache.joe.user.controller.payload.RegistrationPayload;
import edache.joe.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {

    public static final String BASE_URL = "/user";
    public static final String SIGN_UP_URL = "/sign-up";
    public static final String LOGIN_URL = "/login";

    private UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(LOGIN_URL)
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody LoginPayload payload) {
        AuthenticationResponse response = userService.loginUser(payload);
        return ResponseEntity.ok(response);
    }

    @PostMapping(SIGN_UP_URL)
    public ResponseEntity<AuthenticationResponse> signUpUser(@RequestBody RegistrationPayload payload) {
        return ResponseEntity.ok(userService.registerUser(payload));
    }
}
