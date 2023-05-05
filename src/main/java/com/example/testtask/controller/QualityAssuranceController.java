package com.example.testtask.controller;

import com.example.testtask.dto.QualityAssuranceDto;
import com.example.testtask.service.QualityAssuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/qualityassurance")
public class QualityAssuranceController {
    private final QualityAssuranceService qualityAssuranceService;

    @Autowired
    QualityAssuranceController(QualityAssuranceService service) {
        this.qualityAssuranceService = service;
    }

    @PostMapping("/new")
    public QualityAssuranceDto createQualityAssurance(@RequestBody QualityAssuranceDto qualityAssuranceDto) {
        return qualityAssuranceService.addQualityAssurance(qualityAssuranceDto);
    }

    @GetMapping(value = "/all", params = {"page", "size"})
    public List<QualityAssuranceDto> getAllQualityAssurances(@RequestParam("page") int page, @RequestParam("size") int size) {
        return qualityAssuranceService.getAllQualityAssurances(page, size);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteQualityAssurance(@PathVariable long id) {
        return qualityAssuranceService.deleteQualityAssuranceById(id);
    }

    @PutMapping("update/{id}")
    public QualityAssuranceDto updateQualityAssurance(@RequestBody QualityAssuranceDto qualityAssuranceDto, @RequestParam long id) {
        return qualityAssuranceService.updateQualityAssurance(id, qualityAssuranceDto);
    }
}
