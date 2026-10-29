package college.edu.gradefix.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import college.edu.gradefix.domain.Course;
import college.edu.gradefix.repository.CourseRepository;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> courses() {
        return courseRepository.findAll();
    }

    public Optional<Course> course(UUID id) {
        return courseRepository.findById(id);
    }

    public String save(Course course) {
        courseRepository.save(course);
        return "Course saved successfully";
    }

    public Optional<Course> updateCourse(UUID id, Course updatedCourse) {
        Optional<Course> existingCourse = courseRepository.findById(id);
        if (existingCourse.isEmpty()) {
            return Optional.empty();
        }

        updatedCourse.setId(id);
        Course savedCourse = courseRepository.save(updatedCourse);
        return Optional.of(savedCourse);
    }

    public String deleteCourse(UUID id) {
        if (!courseRepository.existsById(id)) {
            return "The course with that id does not exist";
        }
        courseRepository.deleteById(id);
        return "Course deleted successfully";
    }

    public List<Course> unusedCourses() {
        return courseRepository.findAll();
    }
}
