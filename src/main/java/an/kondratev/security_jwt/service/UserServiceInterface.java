package an.kondratev.security_jwt.service;

import an.kondratev.security_jwt.model.User;

import java.util.Optional;

public interface UserServiceInterface {

    Optional<User> findUserById(Long id);

    User saveUser(User user);

    User updateUser(User user);

    void deleteUser(Long id);

    long countUsers();

    Optional<User> findByUsername(String username);
}
