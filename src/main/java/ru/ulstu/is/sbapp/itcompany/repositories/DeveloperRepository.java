package ru.ulstu.is.sbapp.itcompany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ulstu.is.sbapp.itcompany.controllers.developer.DeveloperDTO;
import ru.ulstu.is.sbapp.itcompany.models.Developer;

import java.util.List;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    @Query("SELECT new ru.ulstu.is.sbapp.itcompany.controllers.developer.DeveloperDTO" +
            "(dev.id, dev.firstName, dev.lastName, proj.name, j.name) " +
            "FROM Developer dev " +
            "INNER JOIN Job j " +
            "ON dev.job.name = j.name " +
            "INNER JOIN Project proj " +
            "ON dev.project.name = proj.name " +
            "WHERE proj.name = :projectName AND j.name = :jobName")
    List<DeveloperDTO> findByNameContaining(
            @Param("projectName") String projectName,
            @Param("jobName") String jobName);
}
