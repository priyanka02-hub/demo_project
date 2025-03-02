package com.project.service;

import com.project.model.RoleMaster;
import com.project.model.UserMaster;
import com.project.model.UserPrincipal;
import com.project.model.UserRegistrationDTO;
import com.project.repo.RoleRepository;
import com.project.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

UserRegistrationDTO userDTO;
UserMaster user;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private  JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;


    public String registerUser(UserRegistrationDTO userDTO) {

        RoleMaster role = roleRepository.findByRoleName(userDTO.getRoleName().toUpperCase())
                .orElseThrow(() -> new RuntimeException("Role not found: " + userDTO.getRoleName()));

        // Create a new user and assign the role
        UserMaster user = new UserMaster();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setSalary(userDTO.getSalary());
        user.setDesignation(userDTO.getDesignation());
        user.setRole(role);

        userRepository.save(user);
        // Send email notification for user creation
        emailService.sendUserDetails(user, "Created");

        return "User has been created successfully: " + role.getRoleName();


    }


    public ResponseEntity<?> updateUser(long id, UserMaster updatedUser) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(updatedUser.getName());
                    existingUser.setEmail(updatedUser.getEmail());
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    existingUser.setSalary(updatedUser.getSalary());
                    existingUser.setDesignation(updatedUser.getDesignation());

                    // Prevent changing role
                    updatedUser.setRole(existingUser.getRole());

                    userRepository.save(existingUser);

                    // Send email notification for user update
                    emailService.sendUserDetails(existingUser, "Updated");

                    return ResponseEntity.ok("User has been updated successfully");
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"));
    }

    public String verify(UserMaster user) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        if (authentication.isAuthenticated()) {

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            String role = userPrincipal.getUser().getRole().getRoleName().toUpperCase();
            return jwtService.generateToken(userPrincipal, role);
        }
        return "fail";
    }

    // DELETE USER
    public ResponseEntity<?> deleteUser(long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);

                    // Send email notification for user deletion
                    emailService.sendDeactivationMail(user);

                    return ResponseEntity.ok("User has been deleted successfully");
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to delete user"));
    }


    public String refreshAccessToken(String refreshToken, String role) {
        String username = jwtService.extractUserName(refreshToken);

        UserMaster user = userRepository.findByEmail(username);
        if (user == null) {
            return "Invalid refresh token";
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (jwtService.validateToken(refreshToken, userDetails)) {
            return jwtService.generateToken(userDetails, role);
        }
        return "Invalid refresh token";
    }




}
