package college.edu.gradefix.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import college.edu.gradefix.domain.Lecturer;
import college.edu.gradefix.service.LecturerService;

@RestController
public class LecturerController {
    private final LecturerService lecturerService;

    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @PostMapping("/api/lecturers/save")
    public ResponseEntity<String> saveLecturer(@RequestBody Lecturer lecturer) {
        String message = lecturerService.save(lecturer);
        if (message.equals("Lecturer saved successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }
        return GradefixResponse.reply(message);
    }

    @GetMapping("/api/lecturers/all")
    public List<Lecturer> lecturers() {
        return lecturerService.lecturers();
    }

    @GetMapping("/api/lecturers/{id}")
    public ResponseEntity<?> lecturer(@PathVariable UUID id) {
        Optional<Lecturer> lecturer = lecturerService.lecturer(id);
        if (lecturer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The lecturer with that id does not exist");
        }
        return ResponseEntity.ok(lecturer.get());
    }

    @PutMapping("/api/lecturers/update/{id}")
    public ResponseEntity<?> updateLecturer(@PathVariable UUID id, @RequestBody Lecturer lecturer) {
        Optional<Lecturer> updatedLecturer = lecturerService.updateLecturer(id, lecturer);
        if (updatedLecturer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The lecturer with that id does not exist");
        }
        return ResponseEntity.ok(updatedLecturer.get());
    }

    @DeleteMapping("/api/lecturers/delete/{id}")
    public ResponseEntity<String> deleteLecturer(@PathVariable UUID id) {
        String message = lecturerService.deleteLecturer(id);
        if (message.equals("The lecturer with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/api/lecturers/by-course")
    public List<Lecturer> byCourse(@RequestParam String name) {
        return lecturerService.byCourse(name);
    }

    @PutMapping("/api/lecturers/{lecturerId}/courses/{courseId}")
    public ResponseEntity<String> addCourse(
            @PathVariable UUID lecturerId,
            @PathVariable UUID courseId) {
        return GradefixResponse.reply(lecturerService.addCourse(lecturerId, courseId));
    }

    @PatchMapping("/api/lecturers/{lecturerId}/staff-room")
    public ResponseEntity<String> changeRoom(
            @PathVariable UUID lecturerId,
            @RequestParam int roomNumber) {
        return GradefixResponse.reply(lecturerService.changeRoom(lecturerId, roomNumber));
    }
}
