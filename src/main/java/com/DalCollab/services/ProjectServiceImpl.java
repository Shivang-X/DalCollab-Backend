package com.DalCollab.services;

import com.DalCollab.DTOs.ProjectDTO;
import com.DalCollab.entities.Project;
import com.DalCollab.entities.User;
import com.DalCollab.repositories.ProjectRepo;
import com.DalCollab.repositories.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{

    private final UserRepo userRepo;
    private final ProjectRepo projectRepo;
    private final ModelMapper modelMapper;
    @Override
    public ProjectDTO addProject(ProjectDTO projectDTO) {
        Project project = modelMapper.map(projectDTO, Project.class);

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByEmail(userName);

        project.setDeveloper(user);

        Project savedProject = projectRepo.save(project);
        projectDTO = modelMapper.map(savedProject, ProjectDTO.class);

        return projectDTO;
    }

    @Override
    public String deleteProject(ProjectDTO projectDTO) {
        try{
            projectRepo.deleteById(projectDTO.getId());
            return "Success";
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepo.findAll();
//        List<ProjectDTO> projectDTOs = projects.stream()
//                .map(project -> modelMapper.map(project, ProjectDTO.class))
//                .toList();

        List<ProjectDTO> projectDTOs = new ArrayList<>();
        for (Project project : projects) {
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setId(project.getId());
            projectDTO.setName(project.getName());
            projectDTO.setDescription(project.getDescription());
            projectDTO.setDeveloperName(project.getDeveloper().getUsername());
            projectDTO.setTags(project.getTags());
            projectDTO.setDeveloper(project.getDeveloper());
            projectDTOs.add(projectDTO);
        }

        return projectDTOs;
    }
}
