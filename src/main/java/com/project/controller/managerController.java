package com.project.controller;


import com.project.model.RoleMaster;
import com.project.model.UserMaster;
import com.project.model.UserRegistrationDTO;
import com.project.repo.RoleRepository;
import com.project.repo.UserRepository;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
@PreAuthorize("hasRole('ROLE_MANAGER')")

public class managerController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService service;


    @PostMapping("/createuser")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDTO userDTO) {
        String response = service.registerUser(userDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateuser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody UserMaster updatedUser) {
        return service.updateUser(id, updatedUser);
    }

    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        return userRepo.findById(id)
                .map(user -> {
                    userRepo.delete(user);
                    return ResponseEntity.ok("User has been deleted successfully");
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to delete user"));
    }

    @GetMapping("/getuserbyid/{id}")
    public ResponseEntity<String> getUserById(@PathVariable("id") long id) {
        return userRepo.findById(id)
                .map(user -> ResponseEntity.ok("Success"))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"));
    }

    @GetMapping("/getalluser")
    public ResponseEntity<Object> getAllUsers() {
        List<UserMaster> users = userRepo.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found ");
        }
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }
}





