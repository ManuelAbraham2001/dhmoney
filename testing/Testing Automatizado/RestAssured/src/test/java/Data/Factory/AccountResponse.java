package Data.Factory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponse {
    private String id;
    private String userId;
    private String name;
    private String cvu;
    private String alias;
    private double balance;
}

