package com.HomEasy.controllers;

import com.HomEasy.DTOs.UserDTO;
import com.HomEasy.payloads.AuthResponse;
import com.HomEasy.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @GetMapping("/me")
    public ResponseEntity<AuthResponse> me() {


        if(SecurityContextHolder.getContext().getAuthentication() == null){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(AuthResponse.builder().message("No user found").build());
        }

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO user = userService.getUser(userName);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(AuthResponse.builder().user(user).message("Success").build());

    }
}
