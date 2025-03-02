package com.project.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/tester")
@PreAuthorize("hasRole('ROLE_TESTER')")
public class testerController {

    @GetMapping("/getprojecttest/{id}")
    public ResponseEntity<String> getProjectForTester(@PathVariable("id") long id) {

            String role = SecurityContextHolder.getContext().getAuthentication()
                    .getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse(null);

            if (!"TESTER".equals(role)) {
                return ResponseEntity.ok("Success");
            }

            return ResponseEntity.ok("Only accessible for TESTER role");
        }



    }





