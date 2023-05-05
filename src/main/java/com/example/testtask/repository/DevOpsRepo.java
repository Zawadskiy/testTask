package com.example.testtask.repository;

import com.example.testtask.model.DevOps;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevOpsRepo extends JpaRepository<DevOps, Long> {

    DevOps findByName(String name);
}
