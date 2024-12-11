package kz.bit.kormefinall.controllers;

import kz.bit.kormefinall.models.Category;
import kz.bit.kormefinall.models.Comment;
import kz.bit.kormefinall.models.Product;
import kz.bit.kormefinall.models.User;
import kz.bit.kormefinall.repositories.CommentRepository;
import kz.bit.kormefinall.services.CartService;
import kz.bit.kormefinall.services.CategoryService;
import kz.bit.kormefinall.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor

public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final CommentRepository commentRepository;
    private final CartService cartService;

    @GetMapping("/")
    public String index(Model model) {
        List<Product> products = productService.allProducts();
        List<Category> categories = categoryService.allCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        return "posts/index";
    }
    @GetMapping("/news")
    public String news() {
        return "news";
    }


    @GetMapping("/post/{productId}")
    public String productDetails(@PathVariable Long productId, Model model, @AuthenticationPrincipal User currentUser) {
        List<Category> categories = categoryService.allCategories();
        List<Comment> comments = commentRepository.findByProductId(productId);
        Product product = productService.getProductById(productId);

        boolean isInCart = cartService.isProductInCart(currentUser.getId(), productId);

        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        model.addAttribute("comments", comments);
        model.addAttribute("currentUserId", currentUser.getId());
        model.addAttribute("isInCart", isInCart);

        return "posts/post";
    }


    @GetMapping("/carts")
    public String cart(){
        return "carts/carts";
    }

    @GetMapping("/cartLists")
    public String cartLists(){
        return "admin/cartLists";
    }

    @GetMapping("/category/{categoryId}")
    public String getPostsByCategory(@PathVariable Long categoryId , Model model) {
        Category categories = categoryService.getCategoryById(categoryId);
        List<Product> products = productService.getProductsByCategory(categories);
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "posts/sort";
    }

}
