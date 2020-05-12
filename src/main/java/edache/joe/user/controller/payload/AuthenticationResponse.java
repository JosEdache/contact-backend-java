package edache.joe.user.controller.payload;

import edache.joe.utils.DefaultResponse;
import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
public class AuthenticationResponse extends DefaultResponse {
    private String token;

    public AuthenticationResponse(HttpStatus status, String token) {
        super(status.getReasonPhrase(), status.value(), true);
        this.token = token;
    }

    public AuthenticationResponse(HttpStatus status) {
        super(status.getReasonPhrase(), status.value(), false);
    }
}
