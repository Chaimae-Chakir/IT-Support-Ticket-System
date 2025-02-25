package software.hahn.backend.service;

import org.springframework.transaction.annotation.Transactional;
import software.hahn.backend.dto.TicketDTO;
import software.hahn.backend.enums.Status;
import software.hahn.backend.model.Ticket;
import software.hahn.backend.model.User;

import java.util.List;

public interface TicketService {

    TicketDTO createTicket(Ticket ticket, User user);

    List<TicketDTO> getTicketsByUser(User user);

    List<TicketDTO> getAllTickets();

    TicketDTO getTicketById(Long id);

    @Transactional
    TicketDTO changeStatus(Long ticketId, Status newStatus, User user);

    void createAuditLog(Ticket ticket, String action, User user);
}
