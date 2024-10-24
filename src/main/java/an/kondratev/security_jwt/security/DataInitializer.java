package an.kondratev.security_jwt.security;

import an.kondratev.security_jwt.configuration.SecurityConfig;
import an.kondratev.security_jwt.model.Role;
import an.kondratev.security_jwt.model.User;
import an.kondratev.security_jwt.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final SecurityConfig config;

    @Override
    public void run(String... args) {
        if (userService.countUsers() == 0) {
            User superAdmin = new User();
            superAdmin.setUsername("admin");
            superAdmin.setPassword(config.passwordEncoder().encode("admin_password"));
            superAdmin.setRole(Role.SUPER_ADMIN);
            superAdmin.setAccountNonLocked(true);
            userService.saveUser(superAdmin);
        }
    }
}
