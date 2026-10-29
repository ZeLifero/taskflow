package com.taskflow.service;

import com.taskflow.dto.CreateProjectRequest;
import com.taskflow.dto.ProjectDto;
import com.taskflow.model.Project;
import com.taskflow.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    // Constructor injection (Spring DI) - makes the class easy to unit test
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional(readOnly = true)
    public List<ProjectDto> findAll() {
        return projectRepository.findAll().stream()
                .map(p -> new ProjectDto(p.getId(), p.getName(), p.getDescription()))
                .toList();
    }

    @Transactional
    public ProjectDto create(CreateProjectRequest request) {
        if (projectRepository.existsByName(request.name())) {
            throw new IllegalStateException("Project already exists: " + request.name());
        }
        Project saved = projectRepository.save(new Project(request.name(), request.description()));
        return new ProjectDto(saved.getId(), saved.getName(), saved.getDescription());
    }
}
