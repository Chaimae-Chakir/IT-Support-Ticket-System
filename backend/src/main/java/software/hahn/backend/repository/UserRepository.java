package software.hahn.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import software.hahn.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}