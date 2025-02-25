package software.hahn.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.hahn.backend.dto.TicketDTO;
import software.hahn.backend.enums.Role;
import software.hahn.backend.enums.Status;
import software.hahn.backend.mapper.EntityMapper;
import software.hahn.backend.model.AuditLog;
import software.hahn.backend.model.Ticket;
import software.hahn.backend.model.User;
import software.hahn.backend.repository.AuditLogRepository;
import software.hahn.backend.repository.TicketRepository;
import software.hahn.backend.service.TicketService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final AuditLogRepository auditLogRepository;
    private final EntityMapper entityMapper = EntityMapper.INSTANCE;

    @Override
    public TicketDTO createTicket(Ticket ticket, User user) {
        ticket.setCreatedBy(user);
        ticket.setStatus(Status.NEW);
        Ticket savedTicket = ticketRepository.save(ticket);
        return entityMapper.ticketToTicketDTO(savedTicket);
    }

    @Override
    public List<TicketDTO> getTicketsByUser(User user) {
        List<Ticket> tickets = ticketRepository.findByCreatedByOrderByCreationDateDesc(user);
        return entityMapper.ticketsToTicketDTOs(tickets);
    }

    @Override
    public List<TicketDTO> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAllByOrderByCreationDateDesc();
        return entityMapper.ticketsToTicketDTOs(tickets);
    }

    @Override
    public TicketDTO getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        if (ticket == null) {
            return null;
        }
        return entityMapper.ticketToTicketDTO(ticket);
    }

    @Transactional
    @Override
    public TicketDTO changeStatus(Long ticketId, Status newStatus, User user) {
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if (ticket != null && user.getRole() == Role.IT_SUPPORT) {
            ticket.setStatus(newStatus);
            ticketRepository.save(ticket);
            createAuditLog(ticket, "Status changed to " + newStatus, user);
            return entityMapper.ticketToTicketDTO(ticket);
        }
        return null;
    }

    @Override
    public void createAuditLog(Ticket ticket, String action, User user) {
        AuditLog auditLog = new AuditLog();
        auditLog.setTicket(ticket);
        auditLog.setAction(action);
        auditLog.setUser(user);
        auditLogRepository.save(auditLog);
    }
}

