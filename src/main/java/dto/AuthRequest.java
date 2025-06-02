package dto;

import enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthRequest {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Role role;
    private String profilePictureUrl;
    private List<TeamRequest> teams;
}
