package an.kondratev.security_jwt.service;

import an.kondratev.security_jwt.dto.UserDTO;
import an.kondratev.security_jwt.model.User;
import an.kondratev.security_jwt.repository.UserRepository;
import an.kondratev.security_jwt.security.JWTUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public UserDTO signUp(UserDTO registrationRequest) {
        UserDTO res = new UserDTO();
        User user = new User();
        user.setUsername(registrationRequest.getName());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setRole(registrationRequest.getRole());

        User savedUser = userRepository.save(user);
        if (savedUser.getId() > 0) {
            res.setUser(savedUser);
        }
        return res;
    }

    public UserDTO signIn(UserDTO signRequest) {
        UserDTO response = new UserDTO();
        User user = userRepository.findByUsername(signRequest.getName()).orElseThrow(()
                -> new UsernameNotFoundException("User not found"));

        if (!user.isAccountNonLocked()) {
            if (user.getLockTime() != null && user.getLockTime()
                    .plusMinutes(user.getLOCK_DURATION_MINUTES()).isAfter(LocalDateTime.now())) {
                throw new UsernameNotFoundException(
                        "Account is locked due to multiple failed login attempts. Please try again later."
                );
            } else {

                user.setAccountNonLocked(true);
                user.setFailedLoginAttempts(0);
                user.setLockTime(null);
                userRepository.save(user);
            }
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signRequest.getName(), signRequest.getPassword()));

            user.setFailedLoginAttempts(0);
            userRepository.save(user);

            logger.info("User '{}' successfully signed in.", signRequest.getName());
            var token = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setToken(token);
            response.setTimeToDieToken("2Hr");
            response.setRefreshToken(refreshToken);
            response.setTimeToDieRefreshToken("24Hr");

        } catch (AuthenticationException e) {
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);

            if (user.getFailedLoginAttempts() >= 5) {
                user.setAccountNonLocked(false);
                user.setLockTime(LocalDateTime.now());
                logger.warn("User '{}' has been locked out due to too many failed attempts.", signRequest.getName());
            }

            userRepository.save(user);

            logger.warn("Authentication failed for user '{}': {}", signRequest.getName(), e.getMessage());
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return response;
    }

    public UserDTO refreshToken(UserDTO refreshTokenRequest) {
        UserDTO response = new UserDTO();
        String ourName = jwtUtils.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByUsername(ourName).orElseThrow();
        if (jwtUtils.validateToken(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtUtils.generateToken(user);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenRequest.getRefreshToken());
            response.setTimeToDieToken("2Hr");
        }
        return response;
    }
}

