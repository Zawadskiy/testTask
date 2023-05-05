package com.example.testtask.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "project_developer",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id")
    )
    private Set<Developer> developers = new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "project_devops",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "devops_id")
    )
    private Set<DevOps> devOps = new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "project_qualityassurances",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "qualityassurances_id")
    )
    private Set<QualityAssurance> qualityAssurances = new HashSet<>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Set<Developer> getDevelopers() {
        return developers;
    }

    public Set<DevOps> getDevOps() {
        return devOps;
    }

    public Set<QualityAssurance> getQualityAssurances() {
        return qualityAssurances;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }

    public void setDevOps(Set<DevOps> devOps) {
        this.devOps = devOps;
    }

    public void setQualityAssurances(Set<QualityAssurance> qualityAssurances) {
        this.qualityAssurances = qualityAssurances;
    }

    public void addDevOps(DevOps dev) {
        devOps.add(dev);
        dev.getProjects().add(this);
    }

    public void addQualityAssurances(QualityAssurance qualityAssurance) {
        qualityAssurances.add(qualityAssurance);
        qualityAssurance.getProjects().add(this);
    }

    public void addDeveloper(Developer developer) {
        developers.add(developer);
        developer.getProjects().add(this);
    }

    public void removeDevOps(DevOps dev) {
        devOps.remove(dev);
        dev.getProjects().remove(this);
    }

    public void removeQualityAssurance(QualityAssurance qualityAssurance) {
        qualityAssurances.remove(qualityAssurance);
        qualityAssurance.getProjects().remove(this);
    }

    public void removeDeveloper(Developer developer) {
        developers.remove(developer);
        developer.getProjects().remove(this);
    }

    public void removeAllDevelopers() {
        getDevelopers().forEach(this::removeDeveloper);
    }

    public void removeAllDevOps() {
        getDevOps().forEach(this::removeDevOps);
    }

    public void removeAllQualityAssurances() {
        getQualityAssurances().forEach(this::removeQualityAssurance);
    }
}
