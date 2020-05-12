package edache.joe.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultResponse {
    private String message;
    private int code;
    private boolean success;

    static DefaultResponse successResponse(String message, int code) {
        return new DefaultResponse(message, code, true);
    }

    static DefaultResponse errorResponse(String message, int code) {
        return new DefaultResponse(message, code, false);
    }
}
