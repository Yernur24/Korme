import kz.bit.kormefinall.models.Permission;
import kz.bit.kormefinall.models.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserModelTest {

    @Test
    void loadUserAvatar_withNullAvatar_shouldReturnDefaultAvatar() {
        // Arrange
        User user = new User();
        user.setAvatar(null);

        // Act
        String avatar = user.loadUserAvatar();

        // Assert
        assertEquals("/uti/default-img.jpg", avatar);
    }

    @Test
    void loadUserAvatar_withEmptyAvatar_shouldReturnDefaultAvatar() {
        // Arrange
        User user = new User();
        user.setAvatar("");

        // Act
        String avatar = user.loadUserAvatar();

        // Assert
        assertEquals("/uti/default-img.jpg", avatar);
    }

    @Test
    void loadUserAvatar_withNonEmptyAvatar_shouldReturnAvatar() {
        // Arrange
        User user = new User();
        user.setAvatar("user-avatar.png");

        // Act
        String avatar = user.loadUserAvatar();

        // Assert
        assertEquals("user-avatar.png", avatar);
    }

    @Test
    void getAuthorities_withPermissions_shouldReturnPermissions() {
        // Arrange
        Permission permission1 = new Permission();
        permission1.setRole("ROLE_ADMIN");
        Permission permission2 = new Permission();
        permission2.setRole("ROLE_USER");

        User user = new User();
        user.setPermissions(Arrays.asList(permission1, permission2));

        // Act
        List<Permission> authorities = (List<Permission>) user.getAuthorities();

        // Assert
        assertEquals(2, authorities.size());
        assertTrue(authorities.contains(permission1));
        assertTrue(authorities.contains(permission2));
    }


}