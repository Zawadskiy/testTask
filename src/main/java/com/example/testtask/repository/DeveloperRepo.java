package com.example.testtask.repository;

import com.example.testtask.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeveloperRepo extends JpaRepository<Developer, Long> {

    Developer findByName(String name);
}
