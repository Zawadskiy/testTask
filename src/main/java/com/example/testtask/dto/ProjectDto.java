package com.example.testtask.dto;

import java.util.ArrayList;
import java.util.List;

public class ProjectDto {

    private Long id;

    private List<String> developers = new ArrayList<>();
    private List<String> devOps = new ArrayList<>();
    private List<String> qualityAssurance = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<String> developers) {
        this.developers = developers;
    }

    public List<String> getDevOps() {
        return devOps;
    }

    public void setDevOps(List<String> devOps) {
        this.devOps = devOps;
    }

    public List<String> getQualityAssurance() {
        return qualityAssurance;
    }

    public void setQualityAssurance(List<String> qualityAssurance) {
        this.qualityAssurance = qualityAssurance;
    }
}
