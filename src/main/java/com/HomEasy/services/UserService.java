package com.HomEasy.services;

import com.HomEasy.payloads.AuthResponse;
import com.HomEasy.payloads.LoginCredentials;
import com.HomEasy.DTOs.UserDTO;

public interface UserService {
    AuthResponse registerUser(UserDTO userDTO);
    AuthResponse loginUser(LoginCredentials loginCredentials);
    UserDTO getUser(String email);
}
