package com.DalCollab.repositories;

import com.DalCollab.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Long> {

}
