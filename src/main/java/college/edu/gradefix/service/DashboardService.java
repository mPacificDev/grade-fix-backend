package college.edu.gradefix.service;

import college.edu.gradefix.domain.ClaimStatus;
import college.edu.gradefix.repository.GradeClaimRepository;
import college.edu.gradefix.repository.GradeCorrectionRepository;
import college.edu.gradefix.repository.LecturerRepository;
import college.edu.gradefix.repository.StaffRoomRepository;
import college.edu.gradefix.repository.StudentRepository;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    private final StudentRepository students;
    private final LecturerRepository lecturers;
    private final StaffRoomRepository rooms;
    private final GradeClaimRepository claims;
    private final GradeCorrectionRepository corrections;

    public DashboardService(
            StudentRepository students,
            LecturerRepository lecturers,
            StaffRoomRepository staffRooms,
            GradeClaimRepository gradeClaims,
            GradeCorrectionRepository gradeCorrections) {
        this.students = students;
        this.lecturers = lecturers;
        this.rooms = staffRooms;
        this.claims = gradeClaims;
        this.corrections = gradeCorrections;
    }

    public Map<String, Long> summary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("students", students.count());
        summary.put("lecturers", lecturers.count());
        summary.put("freeStaffRooms", (long) rooms.findFreeRooms().size());
        summary.put("claimsToday", claims.countByClaimDate(LocalDate.now()));
        summary.put("submitted", claims.countByStatus(ClaimStatus.SUBMITTED));
        summary.put("corrections", corrections.count());
        return summary;
    }
}
