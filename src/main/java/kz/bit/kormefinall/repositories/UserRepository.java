package kz.bit.kormefinall.repositories;

import jakarta.transaction.Transactional;
import kz.bit.kormefinall.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}