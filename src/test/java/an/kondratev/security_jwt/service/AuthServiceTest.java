package an.kondratev.security_jwt.service;

import an.kondratev.security_jwt.model.User;
import an.kondratev.security_jwt.security.JWTUtils;
import an.kondratev.security_jwt.repository.UserRepository;
import an.kondratev.security_jwt.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JWTUtils jwtUtils;

    @InjectMocks
    private AuthService authService;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDTO = new UserDTO();
        userDTO.setName("testUser");
        userDTO.setPassword("password123");
        userDTO.setRole("USER");
    }

    @Test
    void whenSignInUserNotFound() {
        when(userRepository.findByUsername(userDTO.getName())).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> authService.signIn(userDTO));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(userDTO.getName());
    }

    @Test
    void whenRefreshToken() {
        User user = new User();
        user.setUsername(userDTO.getName());
        user.setPassword("encryptedPassword");
        user.setRole(userDTO.getRole());

        String refreshToken = "mockRefreshToken";
        when(jwtUtils.extractUsername(refreshToken)).thenReturn(userDTO.getName());
        when(userRepository.findByUsername(userDTO.getName())).thenReturn(Optional.of(user));
        when(jwtUtils.validateToken(refreshToken, user)).thenReturn(true);
        when(jwtUtils.generateToken(user)).thenReturn("newMockToken");

        UserDTO refreshRequest = new UserDTO();
        refreshRequest.setToken(refreshToken);
        refreshRequest.setRefreshToken(refreshToken);

        UserDTO response = authService.refreshToken(refreshRequest);

        assertNotNull(response);
        assertEquals("newMockToken", response.getToken());
    }
}

