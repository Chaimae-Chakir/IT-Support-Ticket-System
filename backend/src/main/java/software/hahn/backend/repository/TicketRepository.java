package software.hahn.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import software.hahn.backend.enums.Status;
import software.hahn.backend.model.Ticket;
import software.hahn.backend.model.User;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByCreatedBy(User user);

    List<Ticket> findByStatus(Status status);

    List<Ticket> findByCreatedByOrderByCreationDateDesc(User user);

    List<Ticket> findAllByOrderByCreationDateDesc();
}