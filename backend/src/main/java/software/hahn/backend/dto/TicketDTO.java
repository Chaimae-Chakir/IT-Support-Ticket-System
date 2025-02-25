package software.hahn.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import software.hahn.backend.enums.Category;
import software.hahn.backend.enums.Priority;
import software.hahn.backend.enums.Status;
import software.hahn.backend.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TicketDTO {
    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private Category category;
    private LocalDateTime creationDate;
    private Status status;
    private User createdBy;
    private List<CommentDTO> comments;
    @JsonIgnore
    private List<AuditLogDTO> auditLogs;
}
