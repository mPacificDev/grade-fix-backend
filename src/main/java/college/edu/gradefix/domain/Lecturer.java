package college.edu.gradefix.domain;

import jakarta.persistence.*;
import java.util.*;

public class Lecturer {
    @Id
    @GeneratedValue (strategy=GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;

    @ManyToOne
    @JoinColumn(name = "staff_room_id")
    private StaffRoom staffRoom;

    @ManyToMany 
    @JoinTable(name="lecturer_course", joinColumns=@JoinColumn(name="lecturer_id"), inverseJoinColumns=@JoinColumn(name="course_id"))
    private List<Course> courses=new ArrayList<>();


    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StaffRoom getStaffRoom() {
        return staffRoom;
    }

    public void setStaffRoom(StaffRoom staffRoom) {
        this.staffRoom = staffRoom;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

}