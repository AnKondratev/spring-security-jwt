package an.kondratev.security_jwt.service;

import an.kondratev.security_jwt.dto.UserDTO;
import an.kondratev.security_jwt.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private AuthService authService;
    private UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (userRepository.findByRole("SUPER_ADMIN").isEmpty()) {
            UserDTO superAdminRequest = new UserDTO();
            superAdminRequest.setName("admin");
            superAdminRequest.setPassword("admin");
            superAdminRequest.setRole("SUPER_ADMIN");

            authService.signUp(superAdminRequest);
        }
    }
}

