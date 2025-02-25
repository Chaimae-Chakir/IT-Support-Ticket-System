package software.hahn.backend.dto;

import lombok.Data;
import software.hahn.backend.enums.Role;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private Role role;
}
