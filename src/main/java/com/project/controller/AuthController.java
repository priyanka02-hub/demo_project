package com.project.controller;


import com.project.model.UserMaster;
import com.project.repo.UserRepository;
import com.project.service.JwtService;
import com.project.service.UserService;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
//@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService service;




    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserMaster user) {
        String token = service.verify(user);

        if ("fail".equals(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body( "Invalid credentials");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successfully");
        response.put("token", token);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + token) // Set JWT token in the header
                .body(response);
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestParam String refreshToken, String role) {
        String newAccessToken = service.refreshAccessToken(refreshToken,role);
        if (newAccessToken.equals("Invalid refresh token")) {
            return ResponseEntity.status(403).body(newAccessToken);
        }
        return ResponseEntity.ok(newAccessToken);
    }


}



