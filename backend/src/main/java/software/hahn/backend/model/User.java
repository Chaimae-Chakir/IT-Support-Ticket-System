package software.hahn.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.hahn.backend.enums.Role;

import java.util.List;

@Entity
@Data
@Table(name = "users")
@JsonIgnoreProperties({"tickets", "comments", "auditLogs"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    // Hashed with BCrypt
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "createdBy")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<AuditLog> auditLogs;
}