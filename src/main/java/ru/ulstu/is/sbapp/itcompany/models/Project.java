package ru.ulstu.is.sbapp.itcompany.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "Difficulty")
    private String difficulty;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Developer> developers;


    public Project() {
    }

    public Project(String name) {
        this.name = name;
    }

    public Project(String name, String difficulty) {
        this.name = name;
        this.difficulty = difficulty;
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }


    public List<Developer> getDevelopers() {
        return developers;
    }


    public void setDeveloper(Developer developer) {
        developers.add(developer);
/*        if (!developers.contains(developer)) {
            developers.add(developer);
            if (developer.getProject() != this) {
                developer.setProject(this);
            }
        }*/
    }

    public Developer removeDeveloper(Long developerID) {
        for (var developer : developers) {
            if (Objects.equals(developer.getID(), developerID)) {
                developers.remove(developer);
                return developer;
            }
        }
        return null;
    }

    public void removeAllDevelopers() {
        developers.clear();
    }

    public void updateDeveloper(Long id, Developer dev) {
        for (var developer : developers) {
            if (Objects.equals(developer.getID(), dev.getID())) {
                developer = dev;
                return;
            }
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Project Project = (Project) o;
        return Objects.equals(id, Project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /*    @Override
        public String toString() {
            return "Проект:\n" +
                    "    id = " + id + "\n" +
                    "    Название = " + name + "\n" +
                    "    Сложность = " + difficulty + "\n";
        }*/
    @Override
    public String toString() {
        return name;
    }
}
