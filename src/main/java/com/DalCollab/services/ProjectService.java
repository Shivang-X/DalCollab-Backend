package com.DalCollab.services;

import com.DalCollab.DTOs.ProjectDTO;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProjectService {

    ProjectDTO addProject(ProjectDTO projectDTO);

    String deleteProject(ProjectDTO projectDTO);
    List<ProjectDTO> getAllProjects();
}
