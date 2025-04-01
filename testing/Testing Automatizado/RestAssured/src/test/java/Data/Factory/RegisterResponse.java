package Data.Factory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String phone;
    private String dni;
    private String email;

    private String accountId;
    private String alias;
    private String cvu;
    private String name;
    private double balance;

    private String accessToken;
}

