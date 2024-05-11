package com.DalCollab.DTOs;

import com.DalCollab.entities.Project;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long userId;
    private String userName;
    private String mobileNumber;
    private String email;
    private String password;
    private String tagline;
    private String bio;
    private List<Project> projects;
    private List<String> skills;

}
