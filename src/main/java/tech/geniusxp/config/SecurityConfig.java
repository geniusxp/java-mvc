package tech.geniusxp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import tech.geniusxp.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/public/**", "/auth/**", "/css/**", "/img/**", "/", "/login").permitAll()
                        .requestMatchers("/users/**", "/events/**", "/coupons/**", "/ticketTypes/**", "/lectures/**", "/speakers/**").hasRole("ADMIN") // Restringe o acesso para ADMIN
                        .requestMatchers("/users/list", "/events/list", "/coupons/list", "/ticketTypes/list", "/lectures/list", "/speakers/list").hasRole("USER") // Restringe o acesso para USER
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login") // Página de login personalizada
                        .defaultSuccessUrl("/") // Página de redirecionamento após sucesso no login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login") // Redirecionamento após logout
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


}
