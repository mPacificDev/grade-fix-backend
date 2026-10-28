package college.edu.gradefix.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name="staff_room")
public class StaffRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String roomNumber;
    private String name;
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}