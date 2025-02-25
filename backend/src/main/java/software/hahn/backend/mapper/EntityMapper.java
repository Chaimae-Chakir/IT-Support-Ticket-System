package software.hahn.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import software.hahn.backend.dto.AuditLogDTO;
import software.hahn.backend.dto.CommentDTO;
import software.hahn.backend.dto.TicketDTO;
import software.hahn.backend.model.AuditLog;
import software.hahn.backend.model.Comment;
import software.hahn.backend.model.Ticket;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EntityMapper {
    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    @Mapping(target = "auditLogs", source = "auditLogs")
    TicketDTO ticketToTicketDTO(Ticket ticket);

    @Mapping(target = "ticket", ignore = true)
    AuditLogDTO auditLogToAuditLogDTO(AuditLog auditLog);

    @Mapping(target = "ticket", ignore = true)
    CommentDTO commentToCommentDTO(Comment comment);

    Ticket ticketDTOToTicket(TicketDTO ticketDTO);

    Comment commentDTOToComment(CommentDTO commentDTO);

    AuditLog auditLogDTOToAuditLog(AuditLogDTO auditLogDTO);

    List<TicketDTO> ticketsToTicketDTOs(List<Ticket> tickets);

    List<CommentDTO> commentsToCommentDTOs(List<Comment> comments);

    List<AuditLogDTO> auditLogsToAuditLogDTOs(List<AuditLog> auditLogs);
}


