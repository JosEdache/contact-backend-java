package edache.joe.user.controller.payload;

import edache.joe.user.User;
import edache.joe.user.UserRole;
import lombok.Data;

@Data
public class RegistrationPayload {
    private String name, email, phoneNumber, password;

    public User createUser() {
        User user = new User();
        user.setName(this.getName());
        user.setEmail(this.getEmail());
        user.setPhoneNumber(this.getPhoneNumber());
        user.setPassword(this.getPassword());
        user.setEnabled(true);
        user.setRole(UserRole.USER);
        return  user;
    }
}
