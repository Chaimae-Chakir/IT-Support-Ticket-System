package software.hahn.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private UserDTO createdBy;
    private TicketDTO ticket;
}
