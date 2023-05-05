package com.example.testtask.service;

import com.example.testtask.dto.DeveloperDto;

import java.util.List;

public interface DeveloperService {

    List<DeveloperDto> getAllDevelopers(int page, int size);

    DeveloperDto addDeveloper(DeveloperDto developerDto);

    String deleteDeveloperById(long developerId);

    DeveloperDto updateDeveloper(long developerId, DeveloperDto developerDto);
}
