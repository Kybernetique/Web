package ru.ulstu.is.sbapp.itcompany.models;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Developer> developers;

    public Job() {
    }

    public Job(String name, Double hourlyRate) {

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

    public List<Developer> getDeveloper() {
        return developers;
    }

    public void setDeveloper(Developer developer) {
        developers.add(developer);
    }


    @Override
    public String toString() {
        return "Должность: " + name;
    }
}
