package ru.ulstu.is.sbapp.itcompany.models.friend;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ulstu.is.sbapp.itcompany.models.Friend;

public class FriendDTO {
    private long id;
    private String firstName;
    private String lastName;
    private int age;

    public FriendDTO() {
    }

    public FriendDTO(Friend friend) {
        this.id = friend.getId();
        this.firstName = friend.getFirstName();
        this.lastName = friend.getLastName();
        this.age = friend.getAge();
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
