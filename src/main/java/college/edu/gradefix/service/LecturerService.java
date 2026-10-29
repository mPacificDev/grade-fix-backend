package college.edu.gradefix.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import college.edu.gradefix.domain.Course;
import college.edu.gradefix.domain.Lecturer;
import college.edu.gradefix.domain.StaffRoom;
import college.edu.gradefix.repository.CourseRepository;
import college.edu.gradefix.repository.LecturerRepository;
import college.edu.gradefix.repository.StaffRoomRepository;

@Service
public class LecturerService {
    private final LecturerRepository lecturerRepository;
    private final StaffRoomRepository staffRoomRepository;
    private final CourseRepository courseRepository;

    public LecturerService(
            LecturerRepository lecturerRepository,
            StaffRoomRepository staffRoomRepository,
            CourseRepository courseRepository) {
        this.lecturerRepository = lecturerRepository;
        this.staffRoomRepository = staffRoomRepository;
        this.courseRepository = courseRepository;
    }

    public List<Lecturer> lecturers() {
        return lecturerRepository.findAll();
    }

    public Optional<Lecturer> lecturer(UUID id) {
        return lecturerRepository.findById(id);
    }

    public String save(Lecturer lecturer) {
        lecturerRepository.save(lecturer);
        return "Lecturer saved successfully";
    }

    public Optional<Lecturer> updateLecturer(UUID id, Lecturer updatedLecturer) {
        Optional<Lecturer> existingLecturer = lecturerRepository.findById(id);
        if (existingLecturer.isEmpty()) {
            return Optional.empty();
        }

        updatedLecturer.setId(id);
        Lecturer savedLecturer = lecturerRepository.save(updatedLecturer);
        return Optional.of(savedLecturer);
    }

    public String deleteLecturer(UUID id) {
        if (!lecturerRepository.existsById(id)) {
            return "The lecturer with that id does not exist";
        }
        lecturerRepository.deleteById(id);
        return "Lecturer deleted successfully";
    }

    public List<Lecturer> byCourse(String name) {
        return courseRepository.findLecturersByCourseName(name);
    }

    @Transactional
    public String addCourse(UUID lecturerId, UUID courseId) {
        Optional<Lecturer> lecturerResult = lecturerRepository.findById(lecturerId);
        if (lecturerResult.isEmpty()) {
            return "The lecturer with that id does not exist";
        }
        Optional<Course> courseResult = courseRepository.findById(courseId);
        if (courseResult.isEmpty()) {
            return "That course does not exist";
        }

        Lecturer lecturer = lecturerResult.get();
        if (lecturer.getCourses().contains(courseResult.get())) {
            return "The lecturer already has that course";
        }
        lecturer.getCourses().add(courseResult.get());
        lecturerRepository.save(lecturer);
        return "Course added to the lecturer";
    }

    public String changeRoom(UUID lecturerId, int roomNumber) {
        Optional<Lecturer> lecturerResult = lecturerRepository.findById(lecturerId);
        if (lecturerResult.isEmpty()) {
            return "The lecturer with that id does not exist";
        }
        Optional<StaffRoom> roomResult = staffRoomRepository.findByRoomNumber(String.valueOf(roomNumber));
        if (roomResult.isEmpty()) {
            return "The staff room with that roomNumber does not exist";
        }
        if (lecturerRepository.existsByStaffRoom(roomResult.get())
                && (lecturerResult.get().getStaffRoom() == null
                        || !lecturerResult.get().getStaffRoom().getId().equals(roomResult.get().getId()))) {
            return "That staff room is taken by another lecturer";
        }
        lecturerResult.get().setStaffRoom(roomResult.get());
        lecturerRepository.save(lecturerResult.get());
        return "Staff room changed successfully";
    }
}
