package college.edu.gradefix.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import college.edu.gradefix.domain.StaffRoom;
import college.edu.gradefix.service.StaffRoomService;

@RestController
public class StaffRoomController {
    private final StaffRoomService staffRoomService;

    public StaffRoomController(StaffRoomService staffRoomService) {
        this.staffRoomService = staffRoomService;
    }

    @PostMapping("/api/staff-rooms/save")
    public ResponseEntity<String> saveRoom(@RequestBody StaffRoom room) {
        String message = staffRoomService.save(room);
        if (message.equals("Staff room saved successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }
        return GradefixResponse.reply(message);
    }

    @GetMapping("/api/staff-rooms/all")
    public List<StaffRoom> rooms() {
        return staffRoomService.rooms();
    }

    @GetMapping("/api/staff-rooms/{id}")
    public ResponseEntity<?> room(@PathVariable UUID id) {
        Optional<StaffRoom> room = staffRoomService.room(id);
        if (room.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The staff room with that id does not exist");
        }
        return ResponseEntity.ok(room.get());
    }

    @PutMapping("/api/staff-rooms/update/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable UUID id, @RequestBody StaffRoom room) {
        Optional<StaffRoom> updatedRoom = staffRoomService.updateRoom(id, room);
        if (updatedRoom.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The staff room with that id does not exist");
        }
        return ResponseEntity.ok(updatedRoom.get());
    }

    @DeleteMapping("/api/staff-rooms/delete/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable UUID id) {
        String message = staffRoomService.deleteRoom(id);
        if (message.equals("The staff room with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/api/staff-rooms/free")
    public List<StaffRoom> freeRooms() {
        return staffRoomService.freeRooms();
    }
}
