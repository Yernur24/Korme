package kz.bit.kormefinall.dto;
import kz.bit.kormefinall.models.Category;
import kz.bit.kormefinall.models.Comment;
import lombok.*;

@Getter
@Setter
public class ProductDTO {

    private Long id;
    private String title;
    private String author;
    private String content;
    private String image;
    private double price;
    private Category category;
    private Comment comment;

}