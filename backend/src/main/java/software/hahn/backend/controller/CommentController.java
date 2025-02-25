package software.hahn.backend.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import software.hahn.backend.dto.CommentDTO;
import software.hahn.backend.dto.TicketDTO;
import software.hahn.backend.model.User;
import software.hahn.backend.security.CustomUserDetails;
import software.hahn.backend.service.CommentService;
import software.hahn.backend.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/api/tickets/{ticketId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<CommentDTO> addComment(@PathVariable Long ticketId, @RequestBody String content, Authentication auth) {
        User user = ((CustomUserDetails) auth.getPrincipal()).getUser();
        TicketDTO ticketDTO = ticketService.getTicketById(ticketId);
        if (ticketDTO == null) {
            throw new EntityNotFoundException("Ticket not found with id: " + ticketId);
        }
        CommentDTO commentDTO = commentService.addComment(ticketDTO, content, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDTO);
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable Long ticketId) {
        TicketDTO ticketDTO = ticketService.getTicketById(ticketId);

        if (ticketDTO == null) {
            throw new EntityNotFoundException("Ticket not found with id: " + ticketId);
        }

        List<CommentDTO> comments = commentService.getCommentsByTicket(ticketDTO);
        return ResponseEntity.ok(comments);
    }
}
