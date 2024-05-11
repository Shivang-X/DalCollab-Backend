package com.DalCollab.controllers;

import com.DalCollab.payloads.AuthResponse;
import com.DalCollab.payloads.LoginCredentials;
import com.DalCollab.DTOs.UserDTO;
import com.DalCollab.repositories.UserRepo;
import com.DalCollab.security.JWTUtil;
import com.DalCollab.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.logging.Logger;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerHandler(@Valid @RequestBody UserDTO user){
        try {

            String encodedPass = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPass);

            AuthResponse authresponse = userService.registerUser(user);
            authresponse.setMessage("User registered successfully !!");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(authresponse);

        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(AuthResponse.builder().message(e.getMessage()).build());

        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(HttpServletRequest request, @Valid @RequestBody LoginCredentials credentials){

        try {

            AuthResponse authresponse = userService.loginUser(credentials);
            authresponse.setMessage("Logged in successfully !!");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(authresponse);
        }catch(AuthenticationException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(AuthResponse.builder().message(e.getMessage()).build());

        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Logged out");
    }

    @GetMapping("/")
    public String process() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return "index";
    }
}
