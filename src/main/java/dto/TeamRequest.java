package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamRequest {
    private Long id;
    private String pokemon1;
    private String pokemon2;
    private String pokemon3;
    private String pokemon4;
    private String pokemon5;
    private String pokemon6;
    private Long userId;
}
