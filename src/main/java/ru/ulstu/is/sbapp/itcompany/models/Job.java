package ru.ulstu.is.sbapp.itcompany.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "hourly_rate")
    private Double hourlyRate;

    public Job() {
    }

    public Job(String name, Double hourlyRate) {

        this.name = name;
        this.hourlyRate = hourlyRate;
    }

    public Job(String name, Double hourlyRate, List<Developer> developers) {

        this.name = name;
        this.hourlyRate = hourlyRate;
    }

    public Long getID() {
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

    public Developer removeDeveloper(Long developerId) {
        return null;
    }

    @Override
    public String toString() {
        return "Должность: " + name;
    }
}
