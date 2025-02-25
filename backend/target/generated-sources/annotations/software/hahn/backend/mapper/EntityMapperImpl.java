package software.hahn.backend.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import software.hahn.backend.dto.AuditLogDTO;
import software.hahn.backend.dto.CommentDTO;
import software.hahn.backend.dto.TicketDTO;
import software.hahn.backend.dto.UserDTO;
import software.hahn.backend.model.AuditLog;
import software.hahn.backend.model.Comment;
import software.hahn.backend.model.Ticket;
import software.hahn.backend.model.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-25T19:20:42+0000",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class EntityMapperImpl implements EntityMapper {

    @Override
    public TicketDTO ticketToTicketDTO(Ticket ticket) {
        if ( ticket == null ) {
            return null;
        }

        TicketDTO ticketDTO = new TicketDTO();

        ticketDTO.setAuditLogs( auditLogsToAuditLogDTOs( ticket.getAuditLogs() ) );
        ticketDTO.setId( ticket.getId() );
        ticketDTO.setTitle( ticket.getTitle() );
        ticketDTO.setDescription( ticket.getDescription() );
        ticketDTO.setPriority( ticket.getPriority() );
        ticketDTO.setCategory( ticket.getCategory() );
        ticketDTO.setCreationDate( ticket.getCreationDate() );
        ticketDTO.setStatus( ticket.getStatus() );
        ticketDTO.setCreatedBy( ticket.getCreatedBy() );
        ticketDTO.setComments( commentsToCommentDTOs( ticket.getComments() ) );

        return ticketDTO;
    }

    @Override
    public AuditLogDTO auditLogToAuditLogDTO(AuditLog auditLog) {
        if ( auditLog == null ) {
            return null;
        }

        AuditLogDTO auditLogDTO = new AuditLogDTO();

        auditLogDTO.setId( auditLog.getId() );
        auditLogDTO.setAction( auditLog.getAction() );
        auditLogDTO.setTimestamp( auditLog.getTimestamp() );
        auditLogDTO.setUser( userToUserDTO( auditLog.getUser() ) );

        return auditLogDTO;
    }

    @Override
    public CommentDTO commentToCommentDTO(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId( comment.getId() );
        commentDTO.setContent( comment.getContent() );
        commentDTO.setCreatedAt( comment.getCreatedAt() );
        commentDTO.setCreatedBy( userToUserDTO( comment.getCreatedBy() ) );

        return commentDTO;
    }

    @Override
    public Ticket ticketDTOToTicket(TicketDTO ticketDTO) {
        if ( ticketDTO == null ) {
            return null;
        }

        Ticket ticket = new Ticket();

        ticket.setId( ticketDTO.getId() );
        ticket.setTitle( ticketDTO.getTitle() );
        ticket.setDescription( ticketDTO.getDescription() );
        ticket.setPriority( ticketDTO.getPriority() );
        ticket.setCategory( ticketDTO.getCategory() );
        ticket.setCreationDate( ticketDTO.getCreationDate() );
        ticket.setStatus( ticketDTO.getStatus() );
        ticket.setCreatedBy( ticketDTO.getCreatedBy() );
        ticket.setComments( commentDTOListToCommentList( ticketDTO.getComments() ) );
        ticket.setAuditLogs( auditLogDTOListToAuditLogList( ticketDTO.getAuditLogs() ) );

        return ticket;
    }

    @Override
    public Comment commentDTOToComment(CommentDTO commentDTO) {
        if ( commentDTO == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setId( commentDTO.getId() );
        comment.setContent( commentDTO.getContent() );
        comment.setCreatedAt( commentDTO.getCreatedAt() );
        comment.setCreatedBy( userDTOToUser( commentDTO.getCreatedBy() ) );
        comment.setTicket( ticketDTOToTicket( commentDTO.getTicket() ) );

        return comment;
    }

    @Override
    public AuditLog auditLogDTOToAuditLog(AuditLogDTO auditLogDTO) {
        if ( auditLogDTO == null ) {
            return null;
        }

        AuditLog auditLog = new AuditLog();

        auditLog.setId( auditLogDTO.getId() );
        auditLog.setTicket( ticketDTOToTicket( auditLogDTO.getTicket() ) );
        auditLog.setAction( auditLogDTO.getAction() );
        auditLog.setTimestamp( auditLogDTO.getTimestamp() );
        auditLog.setUser( userDTOToUser( auditLogDTO.getUser() ) );

        return auditLog;
    }

    @Override
    public List<TicketDTO> ticketsToTicketDTOs(List<Ticket> tickets) {
        if ( tickets == null ) {
            return null;
        }

        List<TicketDTO> list = new ArrayList<TicketDTO>( tickets.size() );
        for ( Ticket ticket : tickets ) {
            list.add( ticketToTicketDTO( ticket ) );
        }

        return list;
    }

    @Override
    public List<CommentDTO> commentsToCommentDTOs(List<Comment> comments) {
        if ( comments == null ) {
            return null;
        }

        List<CommentDTO> list = new ArrayList<CommentDTO>( comments.size() );
        for ( Comment comment : comments ) {
            list.add( commentToCommentDTO( comment ) );
        }

        return list;
    }

    @Override
    public List<AuditLogDTO> auditLogsToAuditLogDTOs(List<AuditLog> auditLogs) {
        if ( auditLogs == null ) {
            return null;
        }

        List<AuditLogDTO> list = new ArrayList<AuditLogDTO>( auditLogs.size() );
        for ( AuditLog auditLog : auditLogs ) {
            list.add( auditLogToAuditLogDTO( auditLog ) );
        }

        return list;
    }

    protected UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setUsername( user.getUsername() );
        userDTO.setRole( user.getRole() );

        return userDTO;
    }

    protected List<Comment> commentDTOListToCommentList(List<CommentDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Comment> list1 = new ArrayList<Comment>( list.size() );
        for ( CommentDTO commentDTO : list ) {
            list1.add( commentDTOToComment( commentDTO ) );
        }

        return list1;
    }

    protected List<AuditLog> auditLogDTOListToAuditLogList(List<AuditLogDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<AuditLog> list1 = new ArrayList<AuditLog>( list.size() );
        for ( AuditLogDTO auditLogDTO : list ) {
            list1.add( auditLogDTOToAuditLog( auditLogDTO ) );
        }

        return list1;
    }

    protected User userDTOToUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userDTO.getId() );
        user.username( userDTO.getUsername() );
        user.role( userDTO.getRole() );

        return user.build();
    }
}
