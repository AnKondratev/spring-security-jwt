package an.kondratev.security_jwt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String username;
    @Column(nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isAccountNonLocked;

}