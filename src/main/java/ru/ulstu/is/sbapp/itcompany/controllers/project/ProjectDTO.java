package ru.ulstu.is.sbapp.itcompany.controllers.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ulstu.is.sbapp.itcompany.models.Project;

public class ProjectDTO {
    private long id;
    private String firstName;
    private String lastName;
    private int age;

    public ProjectDTO() {
    }

    public ProjectDTO(Project project) {
        this.id = project.getId();
        this.firstName = project.getFirstName();
        this.lastName = project.getLastName();
        this.age = project.getAge();
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
