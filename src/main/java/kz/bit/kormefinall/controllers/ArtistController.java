package kz.bit.kormefinall.controllers;


import kz.bit.kormefinall.models.Category;
import kz.bit.kormefinall.models.Product;
import kz.bit.kormefinall.services.CategoryService;
import kz.bit.kormefinall.services.FileService;
import kz.bit.kormefinall.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArtistController {

    @Value("${posts.images.path}")
    private String postsImagesPath;

    private final ProductService productService;
    private final CategoryService categoryService;
    private final FileService fileService;

    @PreAuthorize("hasAnyRole('ROLE_ARTIST' )")
    @GetMapping("/add-products")
    public String addProducts(Model model) {
        List<Category> categories = categoryService.allCategories();
        model.addAttribute("categories", categories);
        return "posts/add-products";
    }

    @PreAuthorize("hasAnyRole('ROLE_ARTIST' , 'ROLE_ADMIN')")
    @PostMapping("/delete-product/{productId}")
    public String delete(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return "redirect:/";
    }


@PostMapping("/update")
public String update(@RequestParam(name = "name") String name,
                          @RequestParam(name = "author") String author,
                          @RequestParam(name = "id")Long id,
                          @RequestParam(name = "image") MultipartFile image,
                          @RequestParam(name = "price") double price,
                          @RequestParam(name = "content") String content) {

    try {
        Product post = productService.findProduct(id);
        post.setName(name);
        post.setAuthor(author);
        post.setContent(content);
        post.setPrice(price);
        if (!image.isEmpty()) {
            String fileName = fileService.saveFile(image, postsImagesPath);
            post.setImage(fileName);
        }
        Product savePost = productService.savePost(post);
        if (savePost != null) {
            return "redirect:?/";
        } else {
            return "redirect:/?error";
        }

    } catch (IOException e) {
        System.out.println(e);
        return "redirect:/?error";
    }
}



    @PreAuthorize("hasAnyRole('ROLE_ARTIST')")
    @PostMapping("/addpost")
    public String addProducts(@RequestParam(name = "name") String name,
                              @RequestParam(name = "author") String author,
                              @RequestParam(name = "image") MultipartFile image,
                              @RequestParam(name = "price") double price,
                              @RequestParam(name = "content") String content,
                              @RequestParam(name = "category.id") Long categoryId) {

        try {
            Product post = new Product();
            post.setName(name);
            post.setAuthor(author);
            post.setContent(content);
            post.setPrice(price);

            Category category = categoryService.getCategoryById(categoryId);
            post.setCategory(category);

            if (!image.isEmpty()) {
                String fileName = fileService.saveFile(image, postsImagesPath);
                post.setImage(fileName);
            }

            productService.addPost(post);
            return "redirect:/";
        } catch (IOException e) {
            return "redirect:/error?error";
        }
    }

}
