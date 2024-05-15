package com.DalCollab.controllers;

import com.DalCollab.DTOs.ProjectDTO;
import com.DalCollab.services.ProjectService;
import com.DalCollab.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {

    private final UserService userService;
    private final ProjectService projectService;

    @GetMapping("/projects")
    public List<ProjectDTO> getAllProjects(){
        return projectService.getAllProjects();
    }

}
