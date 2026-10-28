package college.edu.gradefix.repository;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import college.edu.gradefix.domain.GradeClaim;
import college.edu.gradefix.domain.GradeCorrection;

@Repository
public interface GradeCorrectionRepository extends JpaRepository<GradeCorrection, UUID> {
    @Query("SELECT c FROM GradeCorrection c WHERE c.gradeClaim.student.regNumber = :regNumber ORDER BY c.correctedDate DESC")
    List<GradeCorrection> findHistoryByRegNumber(@Param("regNumber") String regNumber);
    boolean existsByGradeClaim(GradeClaim gradeClaim);
}
