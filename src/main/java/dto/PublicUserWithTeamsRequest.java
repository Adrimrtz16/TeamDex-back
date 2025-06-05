package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PublicUserWithTeamsRequest {
    private Long id;
    private String username;
    private String profilePictureUrl;
    private String bio;
    private List<TeamRequest> teams;
}
