package com.project.controller;


import com.project.model.UserMaster;
import com.project.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/developer")
@PreAuthorize("hasRole('ROLE_DEVELOPER')")
public class developerController {

    @GetMapping("/getprojectdevelop/{id}")
    public ResponseEntity<String> getProjectForDeveloper(@PathVariable("id") long id) {
        String role = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse(null);

        if (!"DEVELOPER".equals(role)) {
            return ResponseEntity.ok("Success");
        }

        return ResponseEntity.ok("Only accessible for DEVELOPER role");
    }
}