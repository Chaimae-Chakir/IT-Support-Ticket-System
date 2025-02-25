package software.hahn.backend.service;

import software.hahn.backend.dto.CommentDTO;
import software.hahn.backend.dto.TicketDTO;
import software.hahn.backend.model.Ticket;
import software.hahn.backend.model.User;

import java.util.List;

public interface CommentService {
    CommentDTO addComment(TicketDTO ticketDTO, String content, User user);

    void createAuditLog(Ticket ticket, String action, User user);

    List<CommentDTO> getCommentsByTicket(TicketDTO ticketDTO);
}
