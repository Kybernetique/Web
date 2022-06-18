package ru.ulstu.is.sbapp.itcompany.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
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
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "friend_fk")
    private List<Developer> developers = new ArrayList<>();

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


    public List<Developer> getDevelopers(){
        return developers;
    }

    public void setDeveloper(Developer developer){
        if(!developers.contains(developer))
        {
            developers.add(developer);
            if(developer.getFriend() != this)
            {
                developer.setFriend(this);
            }
        }
    }

    public Developer removeDeveloper(Long developerId) {
        for (var dev : developers) {
            if (Objects.equals(dev.getId(), developerId)){
                developers.remove(dev);
                return dev;
            }
        }
        return null;
    }

    public void updateDeveloper(Long id, Developer d) {
        for (var dev : developers) {
            if(Objects.equals(dev.getId(), d.getId())) {
                dev = d;
                return;
            }
        }
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
