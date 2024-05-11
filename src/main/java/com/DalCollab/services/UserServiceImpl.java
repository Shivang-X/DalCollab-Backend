package com.DalCollab.services;

import com.DalCollab.entities.User;
import com.DalCollab.exception.APIException;
import com.DalCollab.payloads.AuthResponse;
import com.DalCollab.payloads.LoginCredentials;
import com.DalCollab.DTOs.UserDTO;
import com.DalCollab.repositories.UserRepo;
import com.DalCollab.security.JWTConfig;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

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
            userDTO.setPassword(null);

            return AuthResponse.builder()
                    .user(userDTO)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

        } catch (DataIntegrityViolationException e){
            throw new APIException("User already exists with emailId: " + userDTO.getEmail());
        }catch(ConstraintViolationException e){
            throw new APIException(e.getConstraintViolations().toString());
        }catch(Exception e){
            throw new RuntimeException("Internal server Error");
        }
    }

    @Override
    public AuthResponse loginUser(LoginCredentials credentials) {

        UsernamePasswordAuthenticationToken authCredentials = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());
        authenticationManager.authenticate(authCredentials);

        User user = userRepo.findByEmail(credentials.getEmail());
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setPassword(null);

        String accessToken = jwtConfig.generateToken(user);
        String refreshToken = jwtConfig.generateRefreshToken(user);

        return AuthResponse.builder()
                .user(userDTO)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .message("Logged in successfully")
                .build();
    }

    @Override
    public UserDTO getUser(String email) {
        try{
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepo.findByEmail(userName);
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            userDTO.setPassword(null);
            System.out.println(userDTO.getUserName());
            return userDTO;
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        try{
            if(SecurityContextHolder.getContext().getAuthentication() == null){
                throw new Exception("User not found");
            }

            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepo.findByEmail(userName);

            user.setUserName(userDTO.getUserName());
            user.setBio(userDTO.getBio());
            user.setTagline(userDTO.getTagline());
            user.setMobileNumber(userDTO.getMobileNumber());

            userRepo.save(user);

            userDTO = modelMapper.map(user, UserDTO.class);
            userDTO.setPassword(null);
            return userDTO;

        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<String> addSkills(List<String> skills) {

        try {

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                throw new Exception("User not found");
            }

            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepo.findByEmail(userName);

            user.setSkills(skills);

            User savedUser = userRepo.save(user);

            return savedUser.getSkills();

        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<String> addInterests(List<String> inerests) {
        try{

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                throw new Exception("User not found");
            }

            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepo.findByEmail(userName);

            user.setInterests(inerests);

            User savedUser = userRepo.save(user);

            return savedUser.getInterests();

        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
