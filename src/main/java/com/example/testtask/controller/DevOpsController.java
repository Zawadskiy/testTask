package com.example.testtask.controller;

import com.example.testtask.dto.DevOpsDto;
import com.example.testtask.service.DevOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devops")
public class DevOpsController {

    private final DevOpsService devOpsService;

    @Autowired
    DevOpsController(DevOpsService service) {
        this.devOpsService = service;
    }

    @PostMapping("/new")
    public DevOpsDto createDeveloper(@RequestBody DevOpsDto devOpsDto) {
        return devOpsService.addDevOps(devOpsDto);
    }

    @GetMapping(value = "/all", params = {"page", "size"})
    public List<DevOpsDto> getAllDevOps(@RequestParam("page") int page, @RequestParam("size") int size) {
        return devOpsService.getAllDevOps(page, size);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDevOps(@PathVariable long id) {
       return devOpsService.deleteDevOpsById(id);
    }

    @PutMapping("update/{id}")
    public DevOpsDto updateDevOps(@RequestBody DevOpsDto devOpsDto, @RequestParam long id) {
        return devOpsService.updateDevOps(id, devOpsDto);
    }
}
