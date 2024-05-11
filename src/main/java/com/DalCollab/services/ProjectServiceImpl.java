package com.DalCollab.services;

import com.DalCollab.DTOs.ProjectDTO;
import com.DalCollab.entities.Project;
import com.DalCollab.entities.User;
import com.DalCollab.repositories.ProjectRepo;
import com.DalCollab.repositories.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
}
