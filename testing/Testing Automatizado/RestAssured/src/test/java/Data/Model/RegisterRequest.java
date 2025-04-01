package Data.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String phone;
    private String dni;
    private String email;
}

