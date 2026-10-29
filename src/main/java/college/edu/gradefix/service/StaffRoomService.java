package college.edu.gradefix.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import college.edu.gradefix.domain.StaffRoom;
import college.edu.gradefix.repository.StaffRoomRepository;

@Service
public class StaffRoomService {
    private final StaffRoomRepository staffRoomRepository;

    public StaffRoomService(StaffRoomRepository staffRoomRepository) {
        this.staffRoomRepository = staffRoomRepository;
    }

    public List<StaffRoom> rooms() {
        return staffRoomRepository.findAll();
    }

    public Optional<StaffRoom> room(UUID id) {
        return staffRoomRepository.findById(id);
    }

    public String save(StaffRoom room) {
        staffRoomRepository.save(room);
        return "Staff room saved successfully";
    }

    public Optional<StaffRoom> updateRoom(UUID id, StaffRoom updatedRoom) {
        Optional<StaffRoom> existingRoom = staffRoomRepository.findById(id);
        if (existingRoom.isEmpty()) {
            return Optional.empty();
        }

        updatedRoom.setId(id);
        StaffRoom savedRoom = staffRoomRepository.save(updatedRoom);
        return Optional.of(savedRoom);
    }

    public String deleteRoom(UUID id) {
        if (!staffRoomRepository.existsById(id)) {
            return "The staff room with that id does not exist";
        }
        staffRoomRepository.deleteById(id);
        return "Staff room deleted successfully";
    }

    public List<StaffRoom> freeRooms() {
        return staffRoomRepository.findFreeRooms();
    }
}
