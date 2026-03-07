package com.example.LearnAuthentication.service;

import com.example.LearnAuthentication.dto.ProjectSearchAndFilterRequest;
import com.example.LearnAuthentication.entity.Project;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    Long createProject(Project project);

    List<Project> getAllProjects();

    Optional<Project> getProjectById(Long id);

    Page<Project> searchAndFilterProject(ProjectSearchAndFilterRequest searchRequest);
}
