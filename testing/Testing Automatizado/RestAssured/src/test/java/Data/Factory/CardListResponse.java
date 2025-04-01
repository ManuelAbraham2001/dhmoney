package Data.Factory;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CardListResponse {
    private UUID id;
    private String name;
    private String number;
    private String expiration;
}
