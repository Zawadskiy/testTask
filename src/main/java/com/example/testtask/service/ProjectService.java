package com.example.testtask.service;

import com.example.testtask.dto.ProjectDto;

import java.util.List;

public interface ProjectService {

    List<ProjectDto> getAllProjects(int page, int size);

    ProjectDto addProject(ProjectDto projectDto);

    String deleteProjectById(long projectId);

    ProjectDto updateProject(long projectId, ProjectDto projectDto);
}
