package com.DalCollab.repositories;

import com.DalCollab.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepo extends JpaRepository<Project, Long> {

}
