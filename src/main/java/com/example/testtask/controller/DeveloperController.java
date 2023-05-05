package com.example.testtask.controller;

import com.example.testtask.dto.DeveloperDto;
import com.example.testtask.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/developer")
public class DeveloperController {
    private final DeveloperService developerService;

    @Autowired
    DeveloperController(DeveloperService service) {
        this.developerService = service;
    }

    @PostMapping("/new")
    public DeveloperDto createDeveloper(@RequestBody DeveloperDto developerDto) {
        return developerService.addDeveloper(developerDto);
    }

    @GetMapping(value = "/all", params = {"page", "size"})
    public List<DeveloperDto> getAllDevelopers(@RequestParam("page") int page, @RequestParam("size") int size) {
        return developerService.getAllDevelopers(page, size);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDeveloper(@PathVariable long id) {
        return developerService.deleteDeveloperById(id);
    }

    @PutMapping("update/{id}")
    public DeveloperDto updateDeveloper(@RequestBody DeveloperDto developerDto, @RequestParam long id) {
        return developerService.updateDeveloper(id, developerDto);
    }
}
