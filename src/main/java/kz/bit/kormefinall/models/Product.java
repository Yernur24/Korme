package kz.bit.kormefinall.models;

import jakarta.persistence.*;
        import lombok.Getter;
        import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String Name;

    @Column(name = "author")
    private String Author;

    @Column(name = "content")
    private String content;

    @Column(name = "image")
    private String image;

    @Column(name = "price")
    private double price;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<Rating> ratings;

    public String loadImage(){
        if (image == null || image.isEmpty()) {
            return "postsim/products/default.png";
        }
        return image;
    }

//    public double getAverageRating() {
//        if (ratings == null || ratings.isEmpty()) {
//            return 0;
//        }
//        return ratings.stream()
//                .mapToInt(Rating::getRating)
//                .average()
//                .orElse(0);
//    }

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Comment> comments = new ArrayList<>();

}

