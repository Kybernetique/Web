package ru.ulstu.is.sbapp.itcompany.controllers.job;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ulstu.is.sbapp.itcompany.models.Job;

import javax.validation.constraints.NotBlank;

public class JobDTO {
    private Long id;

    @NotBlank(message = "Name can't be null or empty")
    private String name;

    private Double hourlyRate;

    public JobDTO() {
    }

    public JobDTO(Job job) {
        this.id = job.getID();
        this.name = job.getName();
        this.hourlyRate = job.getHourlyRate();
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}
