package com.HomEasy.services;

import com.HomEasy.entities.User;
import com.HomEasy.exception.APIException;
import com.HomEasy.payloads.AuthResponse;
import com.HomEasy.payloads.LoginCredentials;
import com.HomEasy.DTOs.UserDTO;
import com.HomEasy.repositories.UserRepo;
import com.HomEasy.security.JWTConfig;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final JWTConfig jwtConfig;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse registerUser(UserDTO userDTO) {

        try{
            User user = modelMapper.map(userDTO, User.class);
            
            User registeredUser = userRepo.save(user);

            String accessToken = jwtConfig.generateToken(registeredUser);
            String refreshToken = jwtConfig.generateRefreshToken(registeredUser);

            userDTO = modelMapper.map(registeredUser, UserDTO.class);

            return AuthResponse.builder()
                    .user(userDTO)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

        } catch (DataIntegrityViolationException e){
            throw new APIException("User already exists with emailId: " + userDTO.getEmail());
        }catch(ConstraintViolationException e){
            throw new APIException(e.getConstraintViolations().toString());
        }
    }

    @Override
    public AuthResponse loginUser(LoginCredentials credentials) {

        UsernamePasswordAuthenticationToken authCredentials = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());
        authenticationManager.authenticate(authCredentials);

        User user = userRepo.findByEmail(credentials.getEmail());
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        String accessToken = jwtConfig.generateToken(user);
        String refreshToken = jwtConfig.generateRefreshToken(user);

        return AuthResponse.builder()
                .user(userDTO)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
