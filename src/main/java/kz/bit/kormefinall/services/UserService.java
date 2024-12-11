package kz.bit.kormefinall.services;

import kz.bit.kormefinall.dto.UserDTO;
import kz.bit.kormefinall.mapper.UserMapper;
import kz.bit.kormefinall.models.Permission;
import kz.bit.kormefinall.models.Product;
import kz.bit.kormefinall.models.User;
import kz.bit.kormefinall.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User Not found");
        }
        if (user.isActive()) {
            throw new UsernameNotFoundException("User is banned");
        }
        return user;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User addUser(User user) {
        User checkUser = userRepository.findByEmail(user.getEmail());
        if (checkUser == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        return null;
    }

    public User updatePassword(String newPassword, String oldPassword) {

        User currentUser = getCurrentSessionUser();
        if(passwordEncoder.matches(oldPassword, currentUser.getPassword())){
            currentUser.setPassword(passwordEncoder.encode(newPassword));
            return userRepository.save(currentUser);
        }
        return null;
    }

    public User getCurrentSessionUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }

    public List<User> allUsers(){
        return userRepository.findAll();
    }

    public List<UserDTO> userListDTO() {
        return userRepository.findAll().stream()
                .map(user -> {
                    UserDTO userDTO = userMapper.toUserDTO(user);

                    List<String> roles = user.getPermissions().stream()
                            .map(Permission::getRole)
                            .collect(Collectors.toList());
                    userDTO.setRoles(roles);

                    return userDTO;
                })
                .collect(Collectors.toList());
    }



    public UserDTO getUser(Long id){
        return userMapper.toUserDTO(userRepository.findById(id).orElse(new User()));
    }


    public void Ban(Long id, boolean banned) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setActive(banned);
            userRepository.save(user);
        }
    }
    public void unBan(Long id, boolean unbanned) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setActive(unbanned);
            userRepository.save(user);
        }
    }

}