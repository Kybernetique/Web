package ru.ulstu.is.sbapp.itcompany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ulstu.is.sbapp.itcompany.models.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

}
