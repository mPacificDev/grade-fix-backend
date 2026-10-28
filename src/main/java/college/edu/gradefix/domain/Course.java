package college.edu.gradefix.domain;

import jakarta.persistence.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="course")
public class Course {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "courses")
    private List<Lecturer> lecturers=new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(List<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    @Override 
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof Course course)) return false;
        return id != null && id.equals(course.id);
    }

    @Override
    public int hashCode(){
        return id==null?System.identityHashCode(this):id.hashCode();
    }
}