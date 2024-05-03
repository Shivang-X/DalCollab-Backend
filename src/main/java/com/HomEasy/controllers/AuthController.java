package com.HomEasy.controllers;

import ch.qos.logback.core.model.Model;
import com.HomEasy.entities.User;
import com.HomEasy.payloads.AuthResponse;
import com.HomEasy.payloads.LoginCredentials;
import com.HomEasy.DTOs.UserDTO;
import com.HomEasy.repositories.UserRepo;
import com.HomEasy.security.JWTUtil;
import com.HomEasy.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.logging.Logger;

import static com.HomEasy.entities.Role.USER;

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
            user.setRole(USER);

            AuthResponse authresponse = userService.registerUser(user);
            authresponse.setMessage("User registered successfully !!");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(authresponse);

        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
//                    .body(AuthResponse.builder().message(e.getMessage()).build());
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

    @GetMapping("/me")
    public ResponseEntity me() {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUser(userName));
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
