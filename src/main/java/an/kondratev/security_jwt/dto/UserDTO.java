package an.kondratev.security_jwt.dto;

import an.kondratev.security_jwt.model.Product;
import an.kondratev.security_jwt.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private String token;
    private String errorMessage;
    private String refreshToken;
    private String timeToDieToken;
    private String timeToDieRefreshToken;
    private String name;
    private String role;
    private String password;
    private List<Product> products;
    private User user;
}
