package Data.Factory;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ActivityResponse {
    private UUID id;
    private UUID userId;
    private Double amount;
    private String name;
    private String dated;
    private String type;
    private String origin;
    private String destination;
}
