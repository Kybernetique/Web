package ru.ulstu.is.sbapp.itcompany.controllers.developer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.ulstu.is.sbapp.itcompany.models.Developer;

import javax.validation.constraints.NotBlank;

public class DeveloperDTO {
    private Long id;
    @NotBlank(message = "Developer's first name can't be null or empty")
    private String firstName;
    @NotBlank(message = "Developer's last name can't be null or empty")
    private String lastName;
    private Long companyID;
    private Long jobID;
    private Long projectID;

    public DeveloperDTO(Developer developer) {
        this.id = developer.getID();
        this.firstName = developer.getFirstName();
        this.lastName = developer.getLastName();
        this.companyID = developer.getCompany().getId();
        this.jobID = developer.getJob().getID();
        this.projectID = developer.getProject().getId();
    }

    public DeveloperDTO() {
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long getID() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getCompanyID() {
        return companyID;
    }

    public Long getJobID() {
        return jobID;
    }

    public Long getProjectID() {
        return projectID;
    }


}
