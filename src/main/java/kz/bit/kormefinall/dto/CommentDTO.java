package kz.bit.kormefinall.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private Long id;
    private String content;
    private Long productId;
    private Long authorId;
    private String authorName;
    private java.time.LocalDateTime createdAt;
}
