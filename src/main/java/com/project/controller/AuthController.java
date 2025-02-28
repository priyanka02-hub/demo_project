package com.project.controller;


import com.project.model.UserMaster;
import com.project.repo.UserRepository;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService service;

    @PostMapping("/login")
    public String login(@RequestBody UserMaster user){
        return service.verify(user);
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
