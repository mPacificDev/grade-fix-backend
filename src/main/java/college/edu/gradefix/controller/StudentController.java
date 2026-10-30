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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import college.edu.gradefix.domain.Student;
import college.edu.gradefix.service.LecturerService;
import college.edu.gradefix.service.StudentService;

@RestController
public class StudentController {
    private final StudentService studentService;
    private final LecturerService lecturerService;

    public StudentController(StudentService studentService, LecturerService lecturerService) {
        this.studentService = studentService;
        this.lecturerService = lecturerService;
    }

    @PostMapping("/api/students/save")
    public ResponseEntity<String> saveStudent(@RequestBody Student student) {
        String message = studentService.save(student);
        if (message.equals("Student saved successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }
        return GradefixResponse.reply(message);
    }

    @GetMapping("/api/students/all")
    public List<Student> students() {
        return studentService.students();
    }

    @GetMapping("/api/students/{id}")
    public ResponseEntity<?> student(@PathVariable UUID id) {
        Optional<Student> student = studentService.student(id);
        if (student.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The student with that id does not exist");
        }
        return ResponseEntity.ok(student.get());
    }

    @PutMapping("/api/students/update/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable UUID id, @RequestBody Student student) {
        Optional<Student> updatedStudent = studentService.updateStudent(id, student);
        if (updatedStudent.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The student with that id does not exist");
        }
        return ResponseEntity.ok(updatedStudent.get());
    }

    @DeleteMapping("/api/students/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable UUID id) {
        String message = studentService.deleteStudent(id);
        if (message.equals("The student with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/api/students/by-group")
    public List<Student> byGroup(@RequestParam("group") String group) {
        return studentService.byGroup(group);
    }

    @GetMapping("/api/students/of-lecturer/{lecturerId}")
    public ResponseEntity<?> ofLecturer(@PathVariable UUID lecturerId) {
        if (lecturerService.lecturer(lecturerId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The lecturer with that id does not exist");
        }
        return ResponseEntity.ok(studentService.ofLecturer(lecturerId));
    }
}
