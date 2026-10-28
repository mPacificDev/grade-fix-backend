package college.edu.gradefix.repository;

import college.edu.gradefix.domain.ClaimStatus;
import college.edu.gradefix.domain.GradeClaim;
import college.edu.gradefix.domain.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository 
public interface GradeClaimRepository extends JpaRepository<GradeClaim, UUID> {
    List<GradeClaim> findByStatusOrderByClaimDateAsc(ClaimStatus status);
    List<GradeClaim> findByClaimDateBetweenOrderByClaimDateAsc(LocalDate start,LocalDate end);
    boolean existsByLecturerAndClaimDateAndStatusNot(Lecturer lecturer,LocalDate claimDate,ClaimStatus status);
    long countByStatus(ClaimStatus status);
    long countByClaimDate(LocalDate claimDate);
    @Query("SELECT g.status, COUNT(g) FROM GradeClaim g GROUP BY g.status")
    List<Object[]> statsByStatus();
    @Modifying
    @Query("UPDATE GradeClaim g SET g.status = collage.edu.gradefix.domain.ClaimStatus.DISMISSED WHERE g.lecturer.id = :lecturerId AND g.claimDate = :date AND g.status <> collage.edu.gradefix.domain.ClaimStatus.RESOLVED")
    int cancelClaims(@Param("lecturerId") UUID lecturerId,@Param("date") LocalDate date);
}
