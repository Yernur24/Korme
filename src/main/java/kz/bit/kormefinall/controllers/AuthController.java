package kz.bit.kormefinall.controllers;

import kz.bit.kormefinall.models.User;
import kz.bit.kormefinall.services.FileService;
import kz.bit.kormefinall.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
@Controller
@RequiredArgsConstructor
public class AuthController {


    @Value("${users.images.path}")
    private String userImagesPath;


    private final UserService userService;
    private final FileService fileService;


    @GetMapping(value = "/sign-in-page")
    public String signinPage() {
        return "auth/login";
    }

    @GetMapping(value = "/sign-up-page")
    public String signupPage() {
        return "auth/register";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/profile")
    public String profilePage() {
        return "profile";
    }

    @GetMapping(value = "/403-page")
    public String accessDenied() {
        return "403";
    }

    @GetMapping(value = "/update-password-page")
    public String updaetPasswordPage() {
        return "update-password";
    }

    @PostMapping(value = "/to-sign-up")
    public String toSignUp(@RequestParam(name = "user_email") String email,
                           @RequestParam(name = "user_password") String password,
                           @RequestParam(name = "user_repeat_password") String repeatPassword,
                           @RequestParam(name = "user_full_name") String fullName) {
        if (password.equals(repeatPassword)) {
            User user = new User();
            user.setEmail(email);
            user.setFullName(fullName);
            user.setPassword(password);
            User newUser = userService.addUser(user);
            if (newUser != null) {
                return "redirect:/sign-up-page?success";
            } else {
                return "redirect:/sign-up-page?emailerror";
            }
        } else {
            return "redirect:/sign-up-page?passworderror";
        }
    }

    @PostMapping(value = "/to-update-password")
    public String toUpdatePassword(
            @RequestParam(name = "user_old_password") String oldPassword,
            @RequestParam(name = "user_new_password") String newPassword,
            @RequestParam(name = "user_repeat_new_password") String repeatNewPassword) {

        if (newPassword.equals(repeatNewPassword)) {


            User user = userService.updatePassword(newPassword, oldPassword);
            if (user != null) {
                return "redirect:/update-password-page?success";
            } else {
                return "redirect:/update-password-page?oldpassworderror";
            }

        } else {
            return "redirect:/update-password-page?passwordmismatch";
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/update/user/")
    public String updateUser(
            @RequestParam("avatar") MultipartFile avatar,
            @RequestParam("full_name") String full_name){
        try {
            User user = userService.getCurrentSessionUser();
            user.setFullName(full_name);
            if (!avatar.isEmpty()) {
                String fileName = fileService.saveFile(avatar, userImagesPath);
                user.setAvatar(fileName);
            }
            userService.saveUser(user);
            return "redirect:/profile";
        } catch (IOException e) {
            return "redirect:/error?error";
        }
    }


}

