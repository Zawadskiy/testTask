package com.example.testtask.service;

import com.example.testtask.dto.ProjectDto;
import com.example.testtask.exception.ProjectNotFoundException;
import com.example.testtask.model.*;
import com.example.testtask.repository.DevOpsRepo;
import com.example.testtask.repository.DeveloperRepo;
import com.example.testtask.repository.ProjectRepo;
import com.example.testtask.repository.QualityAssuranceRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepo projectRepo;
    private final DeveloperRepo developerRepo;
    private final DevOpsRepo devOpsRepo;
    private final QualityAssuranceRepo qualityAssuranceRepo;

    @Autowired
    public ProjectServiceImpl(ProjectRepo projectRepo,
                              DevOpsRepo devOpsRepo,
                              DeveloperRepo developerRepo,
                              QualityAssuranceRepo qualityAssuranceRepo) {
        this.projectRepo = projectRepo;
        this.developerRepo = developerRepo;
        this.qualityAssuranceRepo = qualityAssuranceRepo;
        this.devOpsRepo = devOpsRepo;
    }

    @Override
    @Transactional
    public List<ProjectDto> getAllProjects(int page, int size) {
        List<ProjectDto> response = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Project> content = projectRepo.findAll(pageRequest).getContent();

        content.forEach(project -> {
            ProjectDto projectDto = mapEntityToDto(project);
            response.add(projectDto);
        });

        return response;
    }

    @Override
    @Transactional
    public ProjectDto addProject(ProjectDto projectDto) {
        Project project = new Project();
        mapDtoToEntity(projectDto, project);
        Project savedProject = projectRepo.save(project);

        return mapEntityToDto(savedProject);
    }

    @Override
    @Transactional
    public String deleteProjectById(long projectId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
        project.removeAllDevelopers();
        project.removeAllQualityAssurances();
        project.removeAllDevOps();
        projectRepo.delete(project);

        return "Project with id: %d deleted successfully!".formatted(projectId);
    }

    @Override
    @Transactional
    public ProjectDto updateProject(long projectId, ProjectDto projectDto) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
        project.removeAllDevOps();
        project.removeAllDevelopers();
        project.removeAllQualityAssurances();

        mapDtoToEntity(projectDto, project);

        Project saved = projectRepo.save(project);

        return mapEntityToDto(saved);
    }

    private void mapDtoToEntity(ProjectDto projectDto, Project project) {

        projectDto.getDevelopers().forEach(developerName -> {
            Developer developer = developerRepo.findByName(developerName);
            if (developer == null) {
                developer = new Developer();
                developer.setExperienceLevel(ExperienceLevel.JUNIOR);
            }
            developer.setName(developerName);

            project.addDeveloper(developer);
        });

        projectDto.getDevOps().forEach(devOpsName -> {
            DevOps devOps = devOpsRepo.findByName(devOpsName);
            if (devOps == null) {
                devOps = new DevOps();
                devOps.setExperienceLevel(ExperienceLevel.JUNIOR);
            }
            devOps.setName(devOpsName);

            project.addDevOps(devOps);
        });

        projectDto.getQualityAssurance().forEach(qualityAssuranceName -> {
            QualityAssurance qualityAssurance = qualityAssuranceRepo.findByName(qualityAssuranceName);
            if (qualityAssurance == null) {
                qualityAssurance = new QualityAssurance();
                qualityAssurance.setExperienceLevel(ExperienceLevel.JUNIOR);
            }
            qualityAssurance.setName(qualityAssuranceName);

            project.addQualityAssurances(qualityAssurance);
        });
    }

    private ProjectDto mapEntityToDto(Project project) {
        ProjectDto responseDto = new ProjectDto();
        responseDto.setId(project.getId());
        responseDto.setDevelopers(project.getDevelopers().stream().map(Developer::getName).toList());
        responseDto.setDevOps(project.getDevOps().stream().map(DevOps::getName).toList());
        responseDto.setQualityAssurance(project.getQualityAssurances().stream().map(QualityAssurance::getName).toList());

        return responseDto;
    }

}
