package kz.bit.kormefinall.mapper;

import kz.bit.kormefinall.dto.UserDTO;
import kz.bit.kormefinall.models.User;
import kz.bit.kormefinall.models.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "fullName", target = "title")
    UserDTO toUserDTO(User user);

    @Mapping(source = "title", target = "fullName")
    User toEntityUser(UserDTO userDTO);

    List<UserDTO> toUserDTOList(List<User> users);

    List<User> toEntityUserList(List<UserDTO> userDTOList);

    // Метод для преобразования списка Permission в список строк (ролей)
    default List<String> mapPermissionsToRoles(List<Permission> permissions) {
        if (permissions == null) {
            return null;
        }
        return permissions.stream()
                .map(Permission::getRole)
                .collect(Collectors.toList());
    }

    default List<Permission> mapRolesToPermissions(List<String> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(role -> {
                    Permission permission = new Permission();
                    permission.setRole(role);
                    return permission;
                })
                .collect(Collectors.toList());
    }
}