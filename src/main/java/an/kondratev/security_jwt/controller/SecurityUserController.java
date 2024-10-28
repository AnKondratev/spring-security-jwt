package an.kondratev.security_jwt.controller;

import an.kondratev.security_jwt.dto.UserDTO;
import an.kondratev.security_jwt.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class SecurityUserController {
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody UserDTO signUp) {
        return new ResponseEntity<>(authService.signUp(signUp), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserDTO> signIn(@RequestBody UserDTO signIp) {
        return new ResponseEntity<>(authService.signIn(signIp), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<UserDTO> refreshToken(@RequestBody UserDTO refreshToken) {
        return new ResponseEntity<>(authService.refreshToken(refreshToken), HttpStatus.OK);
    }
}