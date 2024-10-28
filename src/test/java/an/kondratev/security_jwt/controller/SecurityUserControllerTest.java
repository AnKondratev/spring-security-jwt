package an.kondratev.security_jwt.controller;

import an.kondratev.security_jwt.dto.UserDTO;
import an.kondratev.security_jwt.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class SecurityUserControllerTest {

    @InjectMocks
    private SecurityUserController securityUserController;

    @Mock
    private AuthService authService;

    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userDTO = new UserDTO();
        userDTO.setName("testuser");
        userDTO.setPassword("password");
        userDTO.setRole("USER");
    }

    @Test
    public void testSignUp() {
        when(authService.signUp(ArgumentMatchers.any(UserDTO.class))).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = securityUserController.signUp(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    public void testSignIn() {
        UserDTO signedInUser = new UserDTO();
        signedInUser.setToken("token");
        signedInUser.setRefreshToken("refreshToken");

        when(authService.signIn(ArgumentMatchers.any(UserDTO.class))).thenReturn(signedInUser);

        ResponseEntity<UserDTO> response = securityUserController.signIn(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(signedInUser, response.getBody());
    }

    @Test
    public void testRefreshToken() {
        UserDTO refreshedUser = new UserDTO();
        refreshedUser.setToken("newToken");
        refreshedUser.setRefreshToken("newRefreshToken");

        when(authService.refreshToken(ArgumentMatchers.any(UserDTO.class))).thenReturn(refreshedUser);

        ResponseEntity<UserDTO> response = securityUserController.refreshToken(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(refreshedUser, response.getBody());
    }
}