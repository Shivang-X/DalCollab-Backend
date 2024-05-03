package com.HomEasy.payloads;

import com.HomEasy.DTOs.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthResponse {

    private UserDTO user;
    private String accessToken;
    private String refreshToken;
    private String  message;

}
