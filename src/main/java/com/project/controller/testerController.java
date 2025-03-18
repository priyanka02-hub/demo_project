package com.project.controller;


import com.project.model.RoleMaster;
import com.project.model.UserMaster;
import com.project.repo.RoleRepository;
import com.project.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/tester")
@PreAuthorize("hasRole('ROLE_TESTER')")
public class testerController {
   // @Autowired
   // private RoleRepository roleRepository;
    /*@GetMapping("/getprojecttest/{id}")
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
        }*/

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getprojecttest")
    public ResponseEntity<Page<UserMaster>> getProjectForTester(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserMaster> testers = userRepository.findByRoleRoleName("TESTER", pageable);
        return ResponseEntity.ok(testers);
    }
    }





