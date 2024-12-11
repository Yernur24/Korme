package kz.bit.kormefinall.services;

import kz.bit.kormefinall.models.Rating;
import kz.bit.kormefinall.models.Product;
import kz.bit.kormefinall.models.User;
import kz.bit.kormefinall.repositories.RatingRepository;
import kz.bit.kormefinall.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ProductRepository productRepository;

    public Rating addRating(User user, Long productId, int ratingValue) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        Rating rating = new Rating();
        rating.setUser(user);
        rating.setProduct(product);
        rating.setScore(ratingValue);

        return ratingRepository.save(rating);
    }
}
