package ru.ulstu.is.sbapp.itcompany.controllers.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ulstu.is.sbapp.itcompany.models.Project;

public class ProjectDTO {
    private Long projectID;

    private String name;

    private String difficulty;

    private String developers = "";

    public ProjectDTO() {
    }

    public ProjectDTO(Project project) {
        this.projectID = project.getId();
        this.name = project.getName();
        this.difficulty = project.getDifficulty();
        if (project.getDevelopers() != null)
            this.developers = project.getDevelopers().toString();
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long getProjectID() {
        return projectID;
    }

    public String getName() {
        return name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getDevelopers() {
        return developers;
    }
}
