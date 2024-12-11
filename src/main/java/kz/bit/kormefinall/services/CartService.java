package kz.bit.kormefinall.services;

import kz.bit.kormefinall.dto.CartItemResponseDTO;
import kz.bit.kormefinall.dto.CartListsResponseDTO;
import kz.bit.kormefinall.dto.CartResponseDTO;
import kz.bit.kormefinall.models.Cart;
import kz.bit.kormefinall.models.CartItem;
import kz.bit.kormefinall.models.Product;
import kz.bit.kormefinall.models.User;
import kz.bit.kormefinall.repositories.CartRepository;
import kz.bit.kormefinall.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserService userService) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    public Cart getCartByUser(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
    }

    public void addProductToCart(User user, Long productId) {
        Cart cart = getCartByUser(user);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));
        cart.addProduct(product);
        cartRepository.save(cart);
    }

    public void removeProductFromCart(User user, Long productId) {
        Cart cart = getCartByUser(user);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));
        cart.removeProduct(product);
        cartRepository.save(cart);
    }

    public void clearCart(User user) {
        Cart cart = getCartByUser(user);
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    public CartResponseDTO getCartResponseForCurrentUser() {
        User currentUser = userService.getCurrentSessionUser();
        Cart cart = getCartByUser(currentUser);

        List<CartItemResponseDTO> items = cart.getItems().stream()
                .filter(cartItem -> "ACTIVE".equals(cartItem.getStatus()))
                .map(this::convertToCartItemResponse)
                .collect(Collectors.toList());

        if (items.isEmpty()) {
            return new CartResponseDTO(cart.getId(), List.of(), 0.0);
        }

        Double totalPrice = items.stream()
                .mapToDouble(item -> item.getQuantity() * item.getProductPrice())
                .sum();

        return new CartResponseDTO(cart.getId(), items, totalPrice);
    }



    private CartItemResponseDTO convertToCartItemResponse(CartItem cartItem) {
        Product product = cartItem.getProduct();
        return new CartItemResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                cartItem.getQuantity(),
                product.getImage()
        );
    }

    public void completePurchase(User user) {
        Cart cart = getCartByUser(user);

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty. Cannot complete purchase.");
        }

        cart.getItems().forEach(cartItem -> cartItem.setStatus("COMPLETE"));
        cartRepository.save(cart);

    }

    public boolean isProductInCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null || cart.getItems().isEmpty()) {
            return false;
        }
        return cart.getItems().stream()
                .anyMatch(item -> item.getProduct().getId().equals(productId));
    }

    public List<CartListsResponseDTO> getAllCompletedCarts() {
        List<Cart> completedCarts = cartRepository.findByItemsStatus("COMPLETE");

        return completedCarts.stream()
                .flatMap(cart -> cart.getItems().stream().map(cartItem -> {
                    CartListsResponseDTO dto = new CartListsResponseDTO();
                    dto.setCartId(cart.getId());
                    dto.setUserName(cart.getUser().getFullName());
                    dto.setProductName(cartItem.getProduct().getName());
                    dto.setStatus(cartItem.getStatus());
                    dto.setQuantity(cartItem.getQuantity());
                    dto.setPrice(cartItem.getProduct().getPrice());
                    dto.setTotalSum(cart.getItems().stream()
                            .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                            .sum());
                    return dto;
                }))
                .collect(Collectors.toList());
    }
}

