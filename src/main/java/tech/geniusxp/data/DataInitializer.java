package tech.geniusxp.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.geniusxp.models.Role;
import tech.geniusxp.models.User;
import tech.geniusxp.repositories.RoleRepository;
import tech.geniusxp.repositories.UserRepository;

import java.util.Set;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            adminRole.setLabel("Admin");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            userRole.setLabel("User");
            roleRepository.save(userRole);

            User admin = new User();
            admin.setName("Admin");
            admin.setUsername("admin@email.com");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(Set.of(adminRole));
            userRepository.save(admin);

            User user = new User();
            user.setName("John Doe");
            user.setUsername("johndoe@email.com");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
        };
    }
}