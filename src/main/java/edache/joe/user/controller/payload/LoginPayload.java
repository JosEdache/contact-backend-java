package edache.joe.user.controller.payload;

import lombok.Data;

@Data
public class LoginPayload {
    private String username, password;
}
