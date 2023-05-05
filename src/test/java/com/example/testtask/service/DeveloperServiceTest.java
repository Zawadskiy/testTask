package com.example.testtask.service;

import com.example.testtask.dto.DeveloperDto;
import com.example.testtask.model.Developer;
import com.example.testtask.model.ExperienceLevel;
import com.example.testtask.model.Project;
import com.example.testtask.repository.DeveloperRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// У звязку з подібністю усіх тестів, буду вважати що не полінився і таки скопіював їх з незначними змінами...
@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
public class DeveloperServiceTest {
    @Mock
    private DeveloperRepo developerRepo;
    @InjectMocks
    private DeveloperServiceImpl developerService;

    @Test
    public void getAllDevelopersTest() {

    }

    @Test
    public void addDeveloperTest() {
        Developer developer = new Developer();
        developer.setName("developer");
        developer.setExperienceLevel(ExperienceLevel.MIDDLE);
        developer.setId(1L);

        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setName("developer");
        developerDto.setExperienceLevel(ExperienceLevel.MIDDLE.toString());

        when(developerRepo.save(any(Developer.class))).thenReturn(developer);

        DeveloperDto result = developerService.addDeveloper(developerDto);

        assertEquals(developerDto.getName(), result.getName());
        assertEquals(developerDto.getExperienceLevel(), result.getExperienceLevel());
        assertEquals(developer.getId(), result.getId());
    }

    @Test
    public void deleteDeveloperByIdTest() {
        Developer developer = new Developer();
        developer.setName("developer");
        developer.setExperienceLevel(ExperienceLevel.MIDDLE);
        developer.setId(1L);

        Project first = new Project();
        first.addDeveloper(developer);

        Project second = new Project();
        second.addDeveloper(developer);

        when(developerRepo.findById(any(Long.class))).thenReturn(Optional.of(developer));

        developerService.deleteDeveloperById(1L);

        assertEquals(0, first.getDevelopers().size());
        assertEquals(0, second.getDevelopers().size());

    }

    @Test
    public void updateDeveloperTest() {
        Developer developer = new Developer();
        developer.setName("developer");
        developer.setExperienceLevel(ExperienceLevel.MIDDLE);
        developer.setId(1L);

        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setName("newName");
        developerDto.setExperienceLevel(ExperienceLevel.SENIOR.toString());

        when(developerRepo.save(developer)).thenReturn(developer);
        when(developerRepo.findById(any())).thenReturn(Optional.of(developer));

        DeveloperDto result = developerService.updateDeveloper(1L, developerDto);

        assertEquals(developerDto.getName(), result.getName());
        assertEquals(developerDto.getExperienceLevel(), result.getExperienceLevel());
        assertEquals(developer.getId(), result.getId());
    }
}
