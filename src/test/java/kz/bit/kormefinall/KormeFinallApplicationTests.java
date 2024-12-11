package kz.bit.kormefinall;

import kz.bit.kormefinall.dto.ProductDTO;
import kz.bit.kormefinall.dto.UserDTO;
import kz.bit.kormefinall.mapper.PostMapper;
import kz.bit.kormefinall.mapper.UserMapper;
import kz.bit.kormefinall.models.Product;
import kz.bit.kormefinall.models.User;
import kz.bit.kormefinall.repositories.ProductRepository;
import kz.bit.kormefinall.repositories.UserRepository;
import kz.bit.kormefinall.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class KormeFinallApplicationTests {

    @Test
    void contextLoads() {
    }

    @InjectMocks
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PostMapper postMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

//    @Test
//    void checkproductDto(){
//
//        Product product = new Product();
//        product.setId(11L);
//        product.setName("Jaqsy kun");
//        product.setAuthor("Yeskendir");
//        product.setContent("jaqsy kun ar kawan zhaksi");
//        product.setImage("J");
//        product.setPrice(500);
//
//        ProductDTO productDTO = postMapper.toDto(product);
//
//        Assertions.assertEquals(product.getName(), productDTO.getName());
//        Assertions.assertEquals(product.getAuthor(), productDTO.getAuthor());
//        Assertions.assertEquals(product.getContent(), productDTO.getContent());
//        Assertions.assertEquals(product.getImage(), productDTO.getImage());
//        Assertions.assertEquals(product.getPrice(), productDTO.getPrice());
//
//    }

}
