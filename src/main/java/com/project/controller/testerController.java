package com.project.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tester")
@PreAuthorize("hasRole('ROLE_TESTER')")
public class testerController {
    @GetMapping("/getprojecttest/{id}")
    public ResponseEntity<String> getProjectForTester(@PathVariable("id") long id) {
        return ResponseEntity.ok("success");
    }



}
