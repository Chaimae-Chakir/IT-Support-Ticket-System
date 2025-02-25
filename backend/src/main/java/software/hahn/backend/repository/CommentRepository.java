package software.hahn.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import software.hahn.backend.model.Comment;
import software.hahn.backend.model.Ticket;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTicket(Ticket ticket);
}