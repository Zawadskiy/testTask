package com.example.testtask.service;

import com.example.testtask.dto.DevOpsDto;

import java.util.List;

public interface DevOpsService {

    List<DevOpsDto> getAllDevOps(int page, int size);

    DevOpsDto addDevOps(DevOpsDto devOpsDto);

    String deleteDevOpsById(long devOpsId);

    DevOpsDto updateDevOps(long devOpsId, DevOpsDto devOpsDto);
}
