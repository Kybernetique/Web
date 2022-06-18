package ru.ulstu.is.sbapp.itcompany.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
public class Friend {
    @Id
    @SequenceGenerator(name = "project_seq", sequenceName = "project_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
    private Long id;
    @NotBlank(message = "Project name can't be null or empty")
    private String firstName;
    private String lastName;
    private int age;

    public Friend() {
    }

    public Friend(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public  String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName=lastName;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age=age;
    }

    @Override
    public boolean equals(Object p) {
        if (this == p) return true;
        if (p == null || getClass() != p.getClass()) return false;
        Friend friend = (Friend) p;
        return Objects.equals(id, friend.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String toString() {
        return firstName + lastName + age;
    }
}
