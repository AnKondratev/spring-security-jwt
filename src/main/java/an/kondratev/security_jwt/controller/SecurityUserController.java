package an.kondratev.security_jwt.controller;

import an.kondratev.security_jwt.model.User;
import an.kondratev.security_jwt.security.JWTUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class SecurityUserController {
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;

    @PostMapping("login")
    public ResponseEntity<String> authenticate(@RequestBody User loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String token = jwtUtils.createToken(userDetails);

            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: invalid credentials");
        }
    }

    @GetMapping("profile")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> getProfile() {
        return ResponseEntity.ok("This is the user's profile.");
    }

    @GetMapping("moderate")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<String> moderateContent() {
        return ResponseEntity.ok("You can moderate content.");
    }

    @GetMapping("admin")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<String> manageUsers() {
        return ResponseEntity.ok("You can manage users.");
    }
}