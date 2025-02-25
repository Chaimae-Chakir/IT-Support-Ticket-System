package software.hahn.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuditLogDTO {
    private Long id;
    private String action;
    private LocalDateTime timestamp;
    private TicketDTO ticket;
    private UserDTO user;
}
