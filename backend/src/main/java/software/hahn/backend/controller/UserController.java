package software.hahn.backend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.hahn.backend.enums.Role;
import software.hahn.backend.model.User;


@RestController
@RequestMapping("/api")
public class UserController {

    /**
     * Retrieves the currently authenticated user from the provided authentication object.
     *
     * @param authentication the authentication object containing user details
     * @return the authenticated user with username and role, excluding the ID and password
     */
    @GetMapping("/user")
    public User getCurrentUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return User.builder()
                .username(userDetails.getUsername())
                .role(Role.valueOf(userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "")))
                .build();
    }
}