package software.hahn.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import software.hahn.backend.model.AuditLog;
import software.hahn.backend.model.Ticket;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByTicket(Ticket ticket);
}