package kz.bit.kormefinall.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
public class UserDTO {
    private Long id;
    private String email;
    private String title;
    private String avatar;
    private boolean isActive;
    private List<String> roles;

    public String getRoles() {
        return String.join(", ", roles);
    }

    public String getAvatar() {
        if (avatar == null) {
            return "/defaults/default-user.png";
        }
        return avatar;
    }
}