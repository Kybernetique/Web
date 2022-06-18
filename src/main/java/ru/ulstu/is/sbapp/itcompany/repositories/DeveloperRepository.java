package ru.ulstu.is.sbapp.itcompany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ulstu.is.sbapp.itcompany.models.developer.DeveloperDTO;
import ru.ulstu.is.sbapp.itcompany.models.Developer;

import java.util.List;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
}
