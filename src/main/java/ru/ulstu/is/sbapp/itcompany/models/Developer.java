package ru.ulstu.is.sbapp.itcompany.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
public class Developer {
    @Id
    @SequenceGenerator(name = "developer_seq",
            sequenceName = "developer_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "developer_seq")
    private Long id;
    @NotBlank (message="Developer's first name can't be null or empty")
    private String firstName;
    @NotBlank (message="Developer's last name can't be null or empty")
    private String lastName;

    @ManyToOne()
    @JoinColumn(name = "friend_fk")
    private Friend friend;


    public Developer(){
    }

    public Developer(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Friend getFriend(){ return friend; }

    public void setFriend(Friend friend) {
        this.friend= friend;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return Objects.equals(id, developer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String toString() {
        return firstName + " " + lastName;
    }
}
