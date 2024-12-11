package kz.bit.kormefinall.api;

import kz.bit.kormefinall.dto.CartListsResponseDTO;
import kz.bit.kormefinall.dto.UserDTO;
import kz.bit.kormefinall.models.User;
import kz.bit.kormefinall.services.CartService;
import kz.bit.kormefinall.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/users")
public class UsersRestAdminController {

    private final UserService userService;
    private final CartService cartService;

    @PostMapping(value = "/ban")
    public ResponseEntity<?> banUser(@RequestParam(name = "user_id") Long id,
                                     @RequestParam(name = "banned") boolean banned) {
        userService.Ban(id, banned);
        return ResponseEntity.ok("User banned");
    }
    @PostMapping(value = "/unban")
    public ResponseEntity<?> unbanUser(@RequestParam(name = "user_id") Long id,
                                       @RequestParam(name = "banned") boolean unbanned) {
        userService.unBan(id, unbanned);
        return ResponseEntity.ok("User unbanned");
    }
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }


    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.userListDTO());
    }

    @GetMapping("/carts/complete")
    public ResponseEntity<List<CartListsResponseDTO>> getAllCompletedCarts() {
        List<CartListsResponseDTO> completedCarts = cartService.getAllCompletedCarts();

        List<CartListsResponseDTO> activeCarts = completedCarts.stream()
                .filter(cart -> cart.getStatus().equals("ACTIVE"))
                .collect(Collectors.toList());
        List<CartListsResponseDTO> completeCarts = completedCarts.stream()
                .filter(cart -> cart.getStatus().equals("COMPLETE"))
                .collect(Collectors.toList());
        activeCarts.addAll(completeCarts);

        return ResponseEntity.ok(activeCarts);
    }




}
