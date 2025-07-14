package com.wipro.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.dto.LoginRequest;
import com.wipro.model.User;
import com.wipro.service.UserService;

@CrossOrigin(origins = "https://proud-dune-0039f2e00.2.azurestaticapps.net")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            userService.registerUser(user);
            System.out.println("Register endpoint hit with user: " + user);
            return ResponseEntity.ok(Map.of("success", true, "message", "You Have Been Registered"));
        } catch (Exception e) {
            System.err.println("Error during registration: " + e.getMessage());
            return ResponseEntity.status(500).body(Map.of("success", false, "message", "Registration failed!"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(Map.of("success", true, "message", "Login Successful!"));
        } catch (Exception e) {
            System.err.println("Error during login: " + e.getMessage());
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Invalid credentials!"));
        }
    }
}
