package kz.bit.kormefinall.services;

import kz.bit.kormefinall.dto.ProductDTO;
import kz.bit.kormefinall.mapper.PostMapper;
import kz.bit.kormefinall.models.Category;
import kz.bit.kormefinall.models.Product;
import kz.bit.kormefinall.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final PostMapper postMapper;

                //RestController
    public List<ProductDTO> getPosts(){
        return postMapper.toDtoList(productRepository.findAll());
    }
    public ProductDTO getPosts(Long id){
        return postMapper.toDto(productRepository.findById(id).orElse(new Product()));
    }
    public ProductDTO addPosts(ProductDTO post){
        return postMapper.toDto(productRepository.save(postMapper.toModel(post)));
    }
    public ProductDTO updatePosts(ProductDTO post){
        return postMapper.toDto(productRepository.save(postMapper.toModel(post)));
    }
    public void deletePosts(Long id){
        productRepository.deleteById(id);
    }


              //Controller
    public List<Product> allProducts(){
        return productRepository.findAll();
    }
    public List<Product> getProductsByCategory(Category category) {return productRepository.findByCategory(category);}
    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }
    public Product addPost(Product product) {return productRepository.save(product);}
    public Product savePost(Product product) {return productRepository.save(product);}
    public  void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
    public Product findProduct(Long id){
        return  productRepository.findById(id).orElse(null);
    }

}

