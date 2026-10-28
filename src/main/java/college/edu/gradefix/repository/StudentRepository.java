package college.edu.gradefix.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import college.edu.gradefix.domain.Student;
import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    List<Student> findByGroupIgnoreCaseOrderByLastNameAsc(String group);
    @Query("select distinct g.student from GradeClaim g where g.lecturer.id = :lecturerId")
    List<Student> findStudentsOfLecturer(@Param("lecturerId") UUID lecturerId);
    
}
