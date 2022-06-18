package ru.ulstu.is.sbapp.itcompany.models.developer;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ulstu.is.sbapp.itcompany.models.Developer;

import javax.validation.constraints.NotBlank;

public class DeveloperDTO {
    private long id;
    @NotBlank(message = "Model can't be null or empty")
    private String firstName;
    private String lastName;
    private long friend;
    private String friendName;

    public DeveloperDTO() {
    }

    public DeveloperDTO(Developer developer) {
        this.id = developer.getId();
        this.firstName = developer.getFirstName();
        this.lastName = developer.getLastName();
        if (developer.getFriend() != null) {
            friend = developer.getFriend().getId();
            friendName = developer.getFriend().getFirstName();
        }
    }

    public DeveloperDTO(DeveloperDTO developerDTO) {
        this.id = developerDTO.id;
        this.firstName = developerDTO.getFirstName();
        this.lastName = developerDTO.getLastName();
        this.friendName = developerDTO.getFriendName();
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFriend(long friend) {
        this.friend = friend;
    }

    public String getFriendName() {
        return friendName;
    }
}
