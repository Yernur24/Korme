package kz.bit.kormefinall.controllers;

import kz.bit.kormefinall.models.Product;
import kz.bit.kormefinall.models.Rating;
import kz.bit.kormefinall.models.User;
import kz.bit.kormefinall.repositories.ProductRepository;
import kz.bit.kormefinall.repositories.RatingRepository;
import kz.bit.kormefinall.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public String addRating(@RequestParam Long productId, @RequestParam int score, @AuthenticationPrincipal User currentUser) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        User user = currentUser;

        Rating existingRating = ratingRepository.findByProductAndUser(product, user);
        if (existingRating != null) {
            return "You have already rated this product!";
        }

        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setScore(score);

        ratingRepository.save(rating);
        return "Rating added successfully!";
    }

    @GetMapping("/product/{productId}")
    public List<Rating> getRatings(@PathVariable Long productId) {
        return ratingRepository.findByProductId(productId);
    }
    @GetMapping("/product/{productId}/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        double averageRating = ratingRepository.findByProductId(productId).stream()
                .mapToInt(Rating::getScore)
                .average()
                .orElse(0.0);

        return ResponseEntity.ok(averageRating);
    }


}
