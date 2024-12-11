package kz.bit.kormefinall.api;

import kz.bit.kormefinall.dto.ProductDTO;
import kz.bit.kormefinall.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/posts")
@RequiredArgsConstructor
public class PostRestController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDTO> postList(){
        return productService.getPosts();
    }


    @GetMapping(value = "{id}")
    public ProductDTO getPosts (@PathVariable(name = "id") Long id){
        return productService.getPosts(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ProductDTO addPosts(@RequestBody ProductDTO post){
        return productService.addPosts(post);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ProductDTO updatePosts(@RequestBody ProductDTO post){
        return productService.updatePosts(post);
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deletePosts(@PathVariable(name = "id") Long id){
        productService.deletePosts(id);
    }

}