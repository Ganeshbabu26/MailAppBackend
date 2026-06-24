package com.babu.spring_boot_demo.service;

import com.babu.spring_boot_demo.dto.LoginRequest;
import com.babu.spring_boot_demo.dto.RegisterRequest;
import com.babu.spring_boot_demo.entity.UserEntity;
import com.babu.spring_boot_demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(RegisterRequest request)
    {
        if (userRepository.findByEmail(request.getEmail()).isPresent())
        {
            return "Email already exist";
        }

        UserEntity user = new UserEntity(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );
        userRepository.save(user);
        return "Registered successful!";
    }

    public String login(LoginRequest request)
    {
        UserEntity user = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (user==null)
        {
            return "User not found";
        }
        if (passwordEncoder.matches(request.getPassword(), user.getPassword()))
        {
            return "Logged in "+user.getEmail();
        }
        return "Login failed";
    }

}
