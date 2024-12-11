package kz.bit.kormefinall.repositories;

import kz.bit.kormefinall.models.Cart;
import kz.bit.kormefinall.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);

    Cart findByUserId(Long userId);

    Optional<Cart> findByUserIdAndStatus(User user, String status);

    @Query("SELECT c FROM Cart c JOIN c.items i WHERE i.status = 'COMPLETE'")
    List<Cart> findByItemsStatus(String status);
}
