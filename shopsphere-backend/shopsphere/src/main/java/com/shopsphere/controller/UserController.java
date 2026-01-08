package com.shopsphere.controller;

import com.shopsphere.dto.UserDTO;
import com.shopsphere.model.User;
import com.shopsphere.security.CustomUserDetails;
import com.shopsphere.util.SecurityUtils;
import com.shopsphere.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }
        
        User user = userService.findById(userDetails.getId());
        UserDTO dto = convertToDTO(user);
        return ResponseEntity.ok(dto);
    }
    
    @PutMapping("/profile")
    public ResponseEntity<UserDTO> updateProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody User updatedUser) {
        
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }
        
        User user = userService.updateUser(userDetails.getId(), updatedUser);
        UserDTO dto = convertToDTO(user);
        return ResponseEntity.ok(dto);
    }
    
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole().name());
        return dto;
    }
}