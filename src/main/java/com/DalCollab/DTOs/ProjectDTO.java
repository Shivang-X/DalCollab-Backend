package com.DalCollab.DTOs;

import com.DalCollab.entities.User;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectDTO {

    private Long id;
    private String name;
    private String description;
    private List<String> tags;
    private User developer;
    private String developerName;

}
