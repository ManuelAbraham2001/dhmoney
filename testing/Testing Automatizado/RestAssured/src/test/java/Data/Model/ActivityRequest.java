package Data.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityRequest {
    private Double amount;
    private String name;
    private String dated;
    private String type;
    private String origin;
    private String destination;
}
