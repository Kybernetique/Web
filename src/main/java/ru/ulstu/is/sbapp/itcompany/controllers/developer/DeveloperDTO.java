package ru.ulstu.is.sbapp.itcompany.controllers.developer;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ulstu.is.sbapp.itcompany.models.Developer;

import javax.validation.constraints.NotBlank;

public class DeveloperDTO {
    private long id;
    @NotBlank(message = "Model can't be null or empty")
    private String firstName;
    private String lastName;
    private long company;
    private long friend;
    private long job;
    private String companyName;
    private String companyCountry;
    private String projectName;
    private String jobName;
    private Double jobHourlyRate;

    public DeveloperDTO() {
    }

    public DeveloperDTO(Developer developer) {
        this.id = developer.getId();
        this.firstName = developer.getFirstName();
        this.lastName = developer.getLastName();
        if (developer.getCompany() != null) {
            company = developer.getCompany().getId();
            companyName = developer.getCompany().getName();
            companyCountry = developer.getCompany().getCountry();
        }
        if (developer.getFriend() != null) {
            friend = developer.getFriend().getId();
            projectName = developer.getFriend().getFirstName();
        }
        if (developer.getJob() != null) {
            job = developer.getJob().getID();
            jobName = developer.getJob().getName();
            jobHourlyRate = developer.getJob().getHourlyRate();
        }
    }

    public DeveloperDTO(DeveloperDTO developerDTO) {
        this.id = developerDTO.id;
        this.firstName = developerDTO.getFirstName();
        this.lastName = developerDTO.getLastName();
        this.projectName = developerDTO.getProjectName();
        this.jobName = developerDTO.getJobName();
    }

    public DeveloperDTO(Long id, String firstName, String lastName,
                        String projectName, String jobName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.projectName = projectName;
        this.jobName=jobName;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getFriend() {
        return friend;
    }

    public long getCompany() {
        return company;
    }

    public long getJob() {
        return job;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFriend(long friend) {
        this.friend = friend;
    }

    public void setCompany(long company) {
        this.company = company;
    }

    public void setJob(long job) {
        this.job = job;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyCountry() {
        return companyCountry;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getJobName() {
        return jobName;
    }

    public Double getJobHourlyRate() {
        return jobHourlyRate;
    }

}
