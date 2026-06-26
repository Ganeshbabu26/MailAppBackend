package com.babu.spring_boot_demo.service;

import com.babu.spring_boot_demo.dto.LoginRequest;
import com.babu.spring_boot_demo.dto.LoginResponse;
import com.babu.spring_boot_demo.dto.RegisterRequest;
import com.babu.spring_boot_demo.entity.UserEntity;
import com.babu.spring_boot_demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exist";
        }

        UserEntity user = new UserEntity(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );
        userRepository.save(user);
        return "Registered successful!";
    }

    public LoginResponse login(
            LoginRequest request)
    {
        LoginResponse response =
                new LoginResponse();

        UserEntity user =
                userRepository
                        .findByEmail(
                                request.getEmail())
                        .orElse(null);

        if(user == null)
        {
            response.setSuccess(false);
            response.setMessage(
                    "User not found");
            return response;
        }

        if(!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()))
        {
            response.setSuccess(false);
            response.setMessage(
                    "Invalid password");
            return response;
        }

        String token =
                jwtService.generateToken(
                        user.getEmail());

        response.setSuccess(true);
        response.setMessage(
                "Login successful");

        response.setEmail(
                user.getEmail());

        response.setToken(token);

        return response;
    }

}
