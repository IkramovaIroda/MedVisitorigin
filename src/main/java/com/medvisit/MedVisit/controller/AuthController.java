package com.medvisit.MedVisit.controller;

import com.medvisit.MedVisit.dto.UserDto;
import com.medvisit.MedVisit.entity.User;
import com.medvisit.MedVisit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).build();
        }
        UserDto dto = new UserDto(
                user.getUserId(),
                user.getUsername(),
                null, // never expose password
                user.getRole(),
                user.getActive()
        );
        return ResponseEntity.ok(dto);
    }
}
