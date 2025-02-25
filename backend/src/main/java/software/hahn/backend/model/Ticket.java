package software.hahn.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import software.hahn.backend.enums.Category;
import software.hahn.backend.enums.Priority;
import software.hahn.backend.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Category category;

    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @JsonBackReference("user-tickets")
    private User createdBy;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    @JsonManagedReference("ticket-comments")
    private List<Comment> comments;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference("ticket-audit")
    private List<AuditLog> auditLogs;

    @PrePersist
    public void prePersist() {
        creationDate = LocalDateTime.now();
        if (status == null) {
            status = Status.NEW;
        }
    }
}