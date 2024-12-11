import kz.bit.kormefinall.controllers.ArtistController;
import kz.bit.kormefinall.models.Category;
import kz.bit.kormefinall.models.Product;
import kz.bit.kormefinall.services.CategoryService;
import kz.bit.kormefinall.services.FileService;
import kz.bit.kormefinall.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ArtistControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private FileService fileService;

    @InjectMocks
    private ArtistController artistController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addProducts_shouldReturnAddProductsTemplate() {
        // Arrange
        Model model = mock(Model.class);
        List<Category> categories = Arrays.asList(new Category(), new Category());
        when(categoryService.allCategories()).thenReturn(categories);

        // Act
        String viewName = artistController.addProducts(model);

        // Assert
        assertEquals("posts/add-products", viewName);
        verify(model).addAttribute("categories", categories);
    }



    // Add more test cases for other controller methods

}