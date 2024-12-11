package kz.bit.kormefinall.api;

import kz.bit.kormefinall.dto.CartResponseDTO;
import kz.bit.kormefinall.models.Cart;
import kz.bit.kormefinall.models.User;
import kz.bit.kormefinall.services.CartService;
import kz.bit.kormefinall.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<CartResponseDTO> viewCart() {
        CartResponseDTO cartResponse = cartService.getCartResponseForCurrentUser();
        return ResponseEntity.ok(cartResponse);
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<String> addToCart(@PathVariable Long productId) {
        User currentUser = userService.getCurrentSessionUser();
        cartService.addProductToCart(currentUser, productId);
        return ResponseEntity.ok("Product added to cart successfully.");
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long productId) {
        User currentUser = userService.getCurrentSessionUser();
        cartService.removeProductFromCart(currentUser, productId);
        return ResponseEntity.ok("Product removed from cart successfully.");
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart() {
        User currentUser = userService.getCurrentSessionUser();
        cartService.clearCart(currentUser);
        return ResponseEntity.ok("Cart cleared successfully.");
    }

    @PostMapping("/complete")
    public ResponseEntity<String> completePurchase() {
        User currentUser = userService.getCurrentSessionUser();

        try {
            cartService.completePurchase(currentUser);
            return ResponseEntity.ok("Purchase completed successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
