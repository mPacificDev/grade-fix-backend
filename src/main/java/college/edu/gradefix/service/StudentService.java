package college.edu.gradefix.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import college.edu.gradefix.domain.Student;
import college.edu.gradefix.repository.StudentRepository;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> students() {
        return studentRepository.findAll();
    }

    public Optional<Student> student(UUID id) {
        return studentRepository.findById(id);
    }

    public String save(Student student) {
        studentRepository.save(student);
        return "Student saved successfully";
    }

    public Optional<Student> updateStudent(UUID id, Student updatedStudent) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        if (existingStudent.isEmpty()) {
            return Optional.empty();
        }

        updatedStudent.setId(id);
        Student savedStudent = studentRepository.save(updatedStudent);
        return Optional.of(savedStudent);
    }

    public String deleteStudent(UUID id) {
        if (!studentRepository.existsById(id)) {
            return "The student with that id does not exist";
        }
        studentRepository.deleteById(id);
        return "Student deleted successfully";
    }

    public List<Student> byGroup(String group) {
        return studentRepository.findByGroupIgnoreCaseOrderByLastNameAsc(group);
    }

    public List<Student> ofLecturer(UUID lecturerId) {
        return studentRepository.findStudentsOfLecturer(lecturerId);
    }
}
