package com.example.testtask.service;

import com.example.testtask.dto.QualityAssuranceDto;
import com.example.testtask.exception.QualityAssuranceNotFoundException;
import com.example.testtask.model.ExperienceLevel;
import com.example.testtask.model.Project;
import com.example.testtask.model.QualityAssurance;
import com.example.testtask.repository.ProjectRepo;
import com.example.testtask.repository.QualityAssuranceRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class QualityAssuranceServiceImpl implements QualityAssuranceService {
    private final QualityAssuranceRepo qualityAssuranceRepo;
    private final ProjectRepo projectRepo;

    @Autowired
    public QualityAssuranceServiceImpl(QualityAssuranceRepo qualityAssuranceRepo, ProjectRepo projectRepo) {
        this.qualityAssuranceRepo = qualityAssuranceRepo;
        this.projectRepo = projectRepo;
    }

    @Override
    @Transactional
    public List<QualityAssuranceDto> getAllQualityAssurances(int page, int size) {
        List<QualityAssuranceDto> response = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(page, size);
        List<QualityAssurance> content = qualityAssuranceRepo.findAll(pageRequest).getContent();

        content.forEach(qualityAssurance -> {
            QualityAssuranceDto qualityAssuranceDto = mapEntityToDto(qualityAssurance);
            response.add(qualityAssuranceDto);
        });

        return response;
    }

    @Override
    @Transactional
    public QualityAssuranceDto addQualityAssurance(QualityAssuranceDto qualityAssuranceDto) {
        QualityAssurance qualityAssurance = new QualityAssurance();
        mapDtoToEntity(qualityAssuranceDto, qualityAssurance);
        QualityAssurance saved = qualityAssuranceRepo.save(qualityAssurance);

        return mapEntityToDto(saved);
    }

    @Override
    @Transactional
    public String deleteQualityAssuranceById(long qualityAssuranceId) {
        QualityAssurance qualityAssurance = qualityAssuranceRepo.findById(qualityAssuranceId)
                .orElseThrow(() -> new QualityAssuranceNotFoundException(qualityAssuranceId));
        new HashSet<>(qualityAssurance.getProjects()).forEach(project -> project.removeQualityAssurance(qualityAssurance));
        qualityAssuranceRepo.delete(qualityAssurance);

        return "QualityAssurance with id: %d deleted successfully!".formatted(qualityAssuranceId);
    }

    @Override
    @Transactional
    public QualityAssuranceDto updateQualityAssurance(long qualityAssuranceId, QualityAssuranceDto qualityAssuranceDto) {
        QualityAssurance qualityAssurance = qualityAssuranceRepo.findById(qualityAssuranceId)
                .orElseThrow(() -> new QualityAssuranceNotFoundException(qualityAssuranceId));
        qualityAssurance.getProjects().forEach(project -> project.removeQualityAssurance(qualityAssurance));
        mapDtoToEntity(qualityAssuranceDto, qualityAssurance);

        QualityAssurance saved = qualityAssuranceRepo.save(qualityAssurance);

        return mapEntityToDto(saved);
    }

    private void mapDtoToEntity(QualityAssuranceDto qualityAssuranceDto, QualityAssurance qualityAssurance) {
        qualityAssurance.setExperienceLevel(ExperienceLevel.valueOf(qualityAssuranceDto.getExperienceLevel()));
        qualityAssurance.setName(qualityAssuranceDto.getName());

        qualityAssuranceDto.getProjects().forEach(projectId -> {
            Project project = projectRepo.findById(Long.getLong(projectId)).orElse(null);
            if (project == null) {
                project = new Project();
            }

            project.addQualityAssurances(qualityAssurance);
        });
    }

    private QualityAssuranceDto mapEntityToDto(QualityAssurance qualityAssurance) {
        QualityAssuranceDto responseDto = new QualityAssuranceDto();
        responseDto.setId(qualityAssurance.getId());
        responseDto.setExperienceLevel(qualityAssurance.getExperienceLevel().toString());
        responseDto.setName(qualityAssurance.getName());
        responseDto.setProjects(qualityAssurance.getProjects().stream().map(project -> project.getId().toString()).toList());

        return responseDto;
    }
}
