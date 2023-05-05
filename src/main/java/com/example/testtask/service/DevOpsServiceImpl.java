package com.example.testtask.service;

import com.example.testtask.dto.DevOpsDto;
import com.example.testtask.exception.DevOpsNotFoundException;
import com.example.testtask.exception.DeveloperNotFoundException;
import com.example.testtask.model.DevOps;
import com.example.testtask.model.ExperienceLevel;
import com.example.testtask.model.Project;
import com.example.testtask.repository.DevOpsRepo;
import com.example.testtask.repository.ProjectRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class DevOpsServiceImpl implements DevOpsService {

    private final DevOpsRepo devOpsRepo;
    private final ProjectRepo projectRepo;

    @Autowired
    public DevOpsServiceImpl(DevOpsRepo devOpsRepo, ProjectRepo projectRepo) {
        this.devOpsRepo = devOpsRepo;
        this.projectRepo = projectRepo;
    }

    @Override
    @Transactional
    public List<DevOpsDto> getAllDevOps(int page, int size) {
        List<DevOpsDto> response = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(page, size);
        List<DevOps> content = devOpsRepo.findAll(pageRequest).getContent();

        content.forEach(devOps -> {
            DevOpsDto devOpsDto = mapEntityToDto(devOps);
            response.add(devOpsDto);
        });

        return response;
    }

    @Override
    @Transactional
    public DevOpsDto addDevOps(DevOpsDto devOpsDto) {
        DevOps DevOps = new DevOps();
        mapDtoToEntity(devOpsDto, DevOps);
        DevOps saved = devOpsRepo.save(DevOps);

        return mapEntityToDto(saved);
    }

    @Override
    @Transactional
    public String deleteDevOpsById(long devOpsId) {
        DevOps DevOps = devOpsRepo.findById(devOpsId).orElseThrow(() -> new DevOpsNotFoundException(devOpsId));
        new HashSet<>(DevOps.getProjects()).forEach(project -> project.removeDevOps(DevOps));
        devOpsRepo.delete(DevOps);

        return "DevOps with id: %d deleted successfully!".formatted(devOpsId);
    }

    @Override
    public DevOpsDto updateDevOps(long devOpsId, DevOpsDto devOpsDto) {
        DevOps devOps = devOpsRepo.findById(devOpsId).orElseThrow(() -> new DeveloperNotFoundException(devOpsId));
        devOps.getProjects().forEach(project -> project.removeDevOps(devOps));
        mapDtoToEntity(devOpsDto, devOps);

        DevOps saved = devOpsRepo.save(devOps);

        return mapEntityToDto(saved);
    }

    private void mapDtoToEntity(DevOpsDto devOpsDto, DevOps devOps) {
        devOps.setExperienceLevel(ExperienceLevel.valueOf(devOpsDto.getExperienceLevel()));
        devOps.setName(devOpsDto.getName());

        devOpsDto.getProjects().forEach(projectId -> {
            Project project = projectRepo.findById(Long.getLong(projectId)).orElse(null);
            if (project == null) {
                project = new Project();
            }

            project.addDevOps(devOps);
        });
    }

    private DevOpsDto mapEntityToDto(DevOps devOps) {
        DevOpsDto responseDto = new DevOpsDto();
        responseDto.setId(devOps.getId());
        responseDto.setExperienceLevel(devOps.getExperienceLevel().toString());
        responseDto.setName(devOps.getName());
        responseDto.setProjects(devOps.getProjects().stream().map(project -> project.getId().toString()).toList());

        return responseDto;
    }
}
