package com.example.testtask.controller;

import com.example.testtask.dto.ProjectDto;
import com.example.testtask.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    ProjectController(ProjectService service) {
        this.projectService = service;
    }

    @PostMapping("/new")
    public ProjectDto createProject(@RequestBody ProjectDto projectDto) {
        return projectService.addProject(projectDto);
    }

    @GetMapping(value = "/all", params = {"page", "size"})
    public List<ProjectDto> getAllProjects(@RequestParam("page") int page, @RequestParam("size") int size) {
        return projectService.getAllProjects(page, size);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProject(@PathVariable long id) {
        return projectService.deleteProjectById(id);
    }

    @PutMapping("update/{id}")
    public ProjectDto updateProject(@RequestBody ProjectDto projectDto, @RequestParam long id) {
        return projectService.updateProject(id, projectDto);
    }
}
