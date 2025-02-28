package com.project.controller;


import com.project.model.UserMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/developer")
@PreAuthorize("hasRole('ROLE_DEVELOPER')")
public class developerController {

    @GetMapping("/getprojectdevelop/{id}")
    public ResponseEntity<String> getProjectForDeveloper(@PathVariable("id") long id) {
        return ResponseEntity.ok("success");
    }




}
