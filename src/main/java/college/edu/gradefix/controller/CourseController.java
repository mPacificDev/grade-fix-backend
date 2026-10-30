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

import college.edu.gradefix.domain.Course;
import college.edu.gradefix.service.CourseService;

@RestController
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/api/courses/save")
    public ResponseEntity<String> saveCourse(@RequestBody Course course) {
        String message = courseService.save(course);
        if (message.equals("Course saved successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }
        return GradefixResponse.reply(message);
    }

    @GetMapping("/api/courses/all")
    public List<Course> courses() {
        return courseService.courses();
    }

    @GetMapping("/api/courses/{id}")
    public ResponseEntity<?> course(@PathVariable UUID id) {
        Optional<Course> course = courseService.course(id);
        if (course.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The course with that id does not exist");
        }
        return ResponseEntity.ok(course.get());
    }

    @PutMapping("/api/courses/update/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable UUID id, @RequestBody Course course) {
        Optional<Course> updatedCourse = courseService.updateCourse(id, course);
        if (updatedCourse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The course with that id does not exist");
        }
        return ResponseEntity.ok(updatedCourse.get());
    }

    @DeleteMapping("/api/courses/delete/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable UUID id) {
        String message = courseService.deleteCourse(id);
        if (message.equals("The course with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/api/courses/unused")
    public List<Course> unused() {
        return courseService.unusedCourses();
    }
}
