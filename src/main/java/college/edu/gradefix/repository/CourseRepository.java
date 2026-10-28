package college.edu.gradefix.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import college.edu.gradefix.domain.Course;
import college.edu.gradefix.domain.Lecturer;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    @Query("select c from Course c where c.lecturers is empty")
    List<Course> findUnUsedCourses();

    @Query("select distinct l from Lecturer l join l.courses c where lower(c.name) = lower(:name)")
    List<Lecturer> findLecturersByCourseName(@Param("name") String name);
}
