package college.edu.gradefix.repository;

import college.edu.gradefix.domain.Lecturer;
import college.edu.gradefix.domain.StaffRoom;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, UUID> {
    boolean existsByStaffRoom(StaffRoom staffRoom);
}
