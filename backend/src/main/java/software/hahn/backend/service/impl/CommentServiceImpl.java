package software.hahn.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.hahn.backend.dto.CommentDTO;
import software.hahn.backend.dto.TicketDTO;
import software.hahn.backend.enums.Role;
import software.hahn.backend.exception.ForbiddenActionException;
import software.hahn.backend.mapper.EntityMapper;
import software.hahn.backend.model.AuditLog;
import software.hahn.backend.model.Comment;
import software.hahn.backend.model.Ticket;
import software.hahn.backend.model.User;
import software.hahn.backend.repository.AuditLogRepository;
import software.hahn.backend.repository.CommentRepository;
import software.hahn.backend.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final AuditLogRepository auditLogRepository;
    private final EntityMapper entityMapper = EntityMapper.INSTANCE;

    @Override
    public CommentDTO addComment(TicketDTO ticketDTO, String content, User user) {
        if (user.getRole() != Role.IT_SUPPORT) {
            throw new ForbiddenActionException("Only IT Support can add comments.");
        }

        Ticket ticket = entityMapper.ticketDTOToTicket(ticketDTO);

        Comment comment = new Comment();
        comment.setTicket(ticket);
        comment.setContent(content);
        comment.setCreatedBy(user);
        commentRepository.save(comment);

        createAuditLog(ticket, "Comment added: " + content, user);
        return entityMapper.commentToCommentDTO(comment);
    }

    @Override
    public void createAuditLog(Ticket ticket, String action, User user) {
        AuditLog auditLog = new AuditLog();
        auditLog.setTicket(ticket);
        auditLog.setAction(action);
        auditLog.setUser(user);
        auditLogRepository.save(auditLog);
    }

    @Override
    public List<CommentDTO> getCommentsByTicket(TicketDTO ticketDTO) {
        Ticket ticket = entityMapper.ticketDTOToTicket(ticketDTO);

        List<Comment> comments = commentRepository.findByTicket(ticket);
        return comments.stream()
                .map(entityMapper::commentToCommentDTO)
                .collect(Collectors.toList());
    }
}