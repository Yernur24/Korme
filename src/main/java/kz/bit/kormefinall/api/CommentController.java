package kz.bit.kormefinall.api;

import kz.bit.kormefinall.dto.CommentDTO;
import kz.bit.kormefinall.mapper.CommentMapper;
import kz.bit.kormefinall.models.Comment;
import kz.bit.kormefinall.models.User;
import kz.bit.kormefinall.repositories.CommentRepository;
import kz.bit.kormefinall.repositories.ProductRepository;
import kz.bit.kormefinall.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final CommentMapper commentMapper;
    private final UserService userService;

    @GetMapping("/product/{productId}")
    public List<CommentDTO> getCommentsByProduct(@PathVariable Long productId) {
        List<Comment> comments = commentRepository.findByProductId(productId);
        return commentMapper.toDtoList(comments);
    }

    @PostMapping
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentDTO commentDTO,
                                                 @AuthenticationPrincipal User currentUser) {
        Comment comment = commentMapper.toModel(commentDTO);
        comment.setAuthor(currentUser);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setProduct(productRepository.findById(commentDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID")));
        comment = commentRepository.save(comment);
        return ResponseEntity.ok(commentMapper.toDto(comment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
