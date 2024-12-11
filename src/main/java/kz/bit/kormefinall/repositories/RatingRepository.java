package kz.bit.kormefinall.repositories;

import kz.bit.kormefinall.models.Product;
import kz.bit.kormefinall.models.Rating;
import kz.bit.kormefinall.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByProductId(Long productId);
    List<Rating> findByUserId(Long userId);

    Rating findByProductAndUser(Product product, User user);
}
