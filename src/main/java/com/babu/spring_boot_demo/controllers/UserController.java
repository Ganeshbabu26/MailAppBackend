package com.babu.spring_boot_demo.controllers;

import java.util.*;

import com.babu.spring_boot_demo.entity.UserEntity;
import com.babu.spring_boot_demo.model.User;
import com.babu.spring_boot_demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController
{
    @Autowired
    private UserRepository userRepository;
}
