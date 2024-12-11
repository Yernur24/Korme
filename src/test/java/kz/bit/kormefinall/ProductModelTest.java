import kz.bit.kormefinall.models.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductModelTest {

    @Test
    void loadImage_withNullImage_shouldReturnDefaultImagePath() {
        // Arrange
        Product product = new Product();
        product.setImage(null);

        // Act
        String imagePath = product.loadImage();

        // Assert
        assertEquals("postsim/products/default.png", imagePath);
    }

    @Test
    void loadImage_withEmptyImage_shouldReturnDefaultImagePath() {
        // Arrange
        Product product = new Product();
        product.setImage("");

        // Act
        String imagePath = product.loadImage();

        // Assert
        assertEquals("postsim/products/default.png", imagePath);
    }

    @Test
    void loadImage_withNonEmptyImage_shouldReturnImage() {
        // Arrange
        Product product = new Product();
        product.setImage("product.png");

        // Act
        String imagePath = product.loadImage();

        // Assert
        assertEquals("product.png", imagePath);
    }

    // Add more test cases for other methods in the Product class

}