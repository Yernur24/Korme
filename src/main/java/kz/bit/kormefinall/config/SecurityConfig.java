package kz.bit.kormefinall.config;

import kz.bit.kormefinall.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserService userService() {
        return new UserService();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.exceptionHandling(exceptions -> exceptions
                .accessDeniedPage("/403-page")
        );

        http.formLogin(form -> form
                .loginPage("/sign-in-page")
                .loginProcessingUrl("/to-enter")
                .usernameParameter("user_email")
                .passwordParameter("user_password")
                .defaultSuccessUrl("/profile")
                .failureUrl("/sign-in-page?autherror")
        );

        http.logout(logout -> logout
                .logoutUrl("/sign-out")
                .logoutSuccessUrl("/sign-in-page")
        );


        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
