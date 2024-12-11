package kz.bit.kormefinall.repositories;

import kz.bit.kormefinall.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
