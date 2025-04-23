package com.example.blog.service;

import com.example.blog.model.User;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository  userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(String username, String email, String password) {
        if (userRepository.existsByUsernameOrEmail(username, email)) {
            throw new IllegalArgumentException("User already exists");
        }
        return userRepository.save(new User(
                username,
                email,
                passwordEncoder.encode(password)
        ));
    }
}

