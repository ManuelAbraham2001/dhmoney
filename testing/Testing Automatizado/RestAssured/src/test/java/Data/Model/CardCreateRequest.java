package Data.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CardCreateRequest {
    private UUID id;
    private String name;
    private String number;
    private String expiration;
    private String cvc;
}

