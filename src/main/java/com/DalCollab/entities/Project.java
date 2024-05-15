package com.DalCollab.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name="project")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 20, message= "Project name must be between 1 and 20 characters long ")
    @NotNull
    private String name;

    @Size(min = 1, max = 1000, message= "Project description must be between 1 and 1000 characters long ")
    @NotNull
    private String description;

    @ElementCollection
    private List<String> tags;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User developer;
}
