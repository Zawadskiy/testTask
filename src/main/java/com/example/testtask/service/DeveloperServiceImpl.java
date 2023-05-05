package com.example.testtask.service;

import com.example.testtask.dto.DeveloperDto;
import com.example.testtask.exception.DeveloperNotFoundException;
import com.example.testtask.model.Developer;
import com.example.testtask.model.ExperienceLevel;
import com.example.testtask.model.Project;
import com.example.testtask.repository.DeveloperRepo;
import com.example.testtask.repository.ProjectRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    private final DeveloperRepo developerRepo;
    private final ProjectRepo projectRepo;

    @Autowired
    public DeveloperServiceImpl(DeveloperRepo developerRepo, ProjectRepo projectRepo) {
        this.developerRepo = developerRepo;
        this.projectRepo = projectRepo;
    }

    @Override
    @Transactional
    public List<DeveloperDto> getAllDevelopers(int page, int size) {
        List<DeveloperDto> response = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Developer> content = developerRepo.findAll(pageRequest).getContent();

        content.forEach(developer -> {
            DeveloperDto developerDto = mapEntityToDto(developer);
            response.add(developerDto);
        });

        return response;
    }

    @Override
    @Transactional
    public DeveloperDto addDeveloper(DeveloperDto developerDto) {
        Developer developer = new Developer();
        mapDtoToEntity(developerDto, developer);
        Developer saved = developerRepo.save(developer);

        return mapEntityToDto(saved);
    }

    @Override
    @Transactional
    public String deleteDeveloperById(long developerId) {
        Developer developer = developerRepo.findById(developerId).orElseThrow(() -> new DeveloperNotFoundException(developerId));
        new HashSet<>(developer.getProjects()).forEach(project -> project.removeDeveloper(developer));
        developerRepo.delete(developer);

        return "Developer with id: %d deleted successfully!".formatted(developerId);
    }

    @Override
    @Transactional
    public DeveloperDto updateDeveloper(long developerId, DeveloperDto developerDto) {
        Developer developer = developerRepo.findById(developerId).orElseThrow(() -> new DeveloperNotFoundException(developerId));
        developer.getProjects().forEach(project -> project.removeDeveloper(developer));
        mapDtoToEntity(developerDto, developer);

        Developer saved = developerRepo.save(developer);

        return mapEntityToDto(saved);
    }

    private void mapDtoToEntity(DeveloperDto developerDto, Developer developer) {
        developer.setExperienceLevel(ExperienceLevel.valueOf(developerDto.getExperienceLevel()));
        developer.setName(developerDto.getName());

        developerDto.getProjects().forEach(projectId -> {
            Project project = projectRepo.findById(Long.getLong(projectId)).orElse(null);
            if (project == null) {
                project = new Project();
            }

            project.addDeveloper(developer);
        });
    }

    private DeveloperDto mapEntityToDto(Developer developer) {
        DeveloperDto responseDto = new DeveloperDto();
        responseDto.setId(developer.getId());
        responseDto.setExperienceLevel(developer.getExperienceLevel().toString());
        responseDto.setName(developer.getName());
        responseDto.setProjects(developer.getProjects().stream().map(project -> project.getId().toString()).toList());

        return responseDto;
    }
}
