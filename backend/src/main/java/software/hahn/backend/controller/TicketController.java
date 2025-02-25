package software.hahn.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import software.hahn.backend.dto.AuditLogDTO;
import software.hahn.backend.dto.TicketDTO;
import software.hahn.backend.enums.Role;
import software.hahn.backend.enums.Status;
import software.hahn.backend.model.Ticket;
import software.hahn.backend.model.User;
import software.hahn.backend.security.CustomUserDetails;
import software.hahn.backend.service.TicketService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/create")
    public TicketDTO createTicket(@RequestBody Ticket ticket, Authentication auth) {
        User user = ((CustomUserDetails) auth.getPrincipal()).getUser();
        return ticketService.createTicket(ticket, user);
    }

    @GetMapping("/my")
    public List<TicketDTO> getMyTickets(Authentication auth) {
        User user = ((CustomUserDetails) auth.getPrincipal()).getUser();
        return ticketService.getTicketsByUser(user);
    }

    @GetMapping("/all")
    public List<TicketDTO> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public TicketDTO getTicketById(@PathVariable Long id) {
        TicketDTO ticketDTO = ticketService.getTicketById(id);
        if (ticketDTO == null) {
            throw new RuntimeException("Ticket not found");
        }
        return ticketDTO;
    }

    @PutMapping("/{id}/status")
    public TicketDTO changeStatus(@PathVariable Long id, @RequestParam Status status, Authentication auth) {
        User user = ((CustomUserDetails) auth.getPrincipal()).getUser();
        return ticketService.changeStatus(id, status, user);
    }

    @GetMapping("/search")
    public List<TicketDTO> searchTickets(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Status status,
            Authentication auth
    ) {
        User user = ((CustomUserDetails) auth.getPrincipal()).getUser();
        List<TicketDTO> tickets = (user.getRole() == Role.IT_SUPPORT)
                ? ticketService.getAllTickets()
                : ticketService.getTicketsByUser(user);

        if (id != null || status != null) {
            return tickets.stream()
                    .filter(t -> (id == null || t.getId().equals(id))
                            && (status == null || t.getStatus() == status))
                    .collect(Collectors.toList());
        }
        return tickets;
    }

    @GetMapping("/{id}/audit")
    public List<AuditLogDTO> getAuditLogs(@PathVariable Long id) {
        TicketDTO ticketDTO = ticketService.getTicketById(id);
        if (ticketDTO == null) {
            throw new RuntimeException("Ticket not found");
        }
        return ticketDTO.getAuditLogs();
    }
}
