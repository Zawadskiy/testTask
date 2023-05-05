package com.example.testtask.repository;

import com.example.testtask.model.QualityAssurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QualityAssuranceRepo extends JpaRepository<QualityAssurance, Long> {
    QualityAssurance findByName(String name);
}
