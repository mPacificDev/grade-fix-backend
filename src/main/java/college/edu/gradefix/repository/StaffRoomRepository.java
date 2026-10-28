package college.edu.gradefix.repository;

import college.edu.gradefix.domain.StaffRoom;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository 
public interface StaffRoomRepository extends JpaRepository<StaffRoom, UUID> {
    Optional<StaffRoom> findByRoomNumber(String roomNumber);
    @Query("select r from StaffRoom r where not exists (select l from Lecturer l where l.staffRoom = r) order by r.roomNumber")
    List<StaffRoom> findFreeRooms();
}
