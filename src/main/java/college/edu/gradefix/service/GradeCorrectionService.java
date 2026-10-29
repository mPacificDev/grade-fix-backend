package college.edu.gradefix.service;

import college.edu.gradefix.domain.ClaimStatus;
import college.edu.gradefix.domain.GradeClaim;
import college.edu.gradefix.domain.GradeCorrection;
import college.edu.gradefix.repository.GradeClaimRepository;
import college.edu.gradefix.repository.GradeCorrectionRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GradeCorrectionService {
    private final GradeCorrectionRepository gradeCorrectionRepository;
    private final GradeClaimRepository gradeClaimRepository;

    public GradeCorrectionService(
            GradeCorrectionRepository gradeCorrectionRepository,
            GradeClaimRepository gradeClaimRepository) {
        this.gradeCorrectionRepository = gradeCorrectionRepository;
        this.gradeClaimRepository = gradeClaimRepository;
    }

    @Transactional
    public String save(GradeCorrection correction, UUID gradeClaimId) {
        Optional<GradeClaim> gradeClaimResult = gradeClaimRepository.findById(gradeClaimId);
        if (gradeClaimResult.isEmpty()) {
            return "The grade claim with that id does not exist";
        }

        GradeClaim gradeClaim = gradeClaimResult.get();
        if (gradeClaim.getStatus() != ClaimStatus.RESOLVED) {
            return "Grade corrections are only for RESOLVED grade claims";
        }

        correction.setGradeClaim(gradeClaim);
        gradeCorrectionRepository.save(correction);
        return "Grade correction saved successfully";
    }

    public List<GradeCorrection> history(String regNumber) {
        return gradeCorrectionRepository.findHistoryByRegNumber(regNumber);
    }

    public String delete(UUID id) {
        if (!gradeCorrectionRepository.existsById(id)) {
            return "The grade correction with that id does not exist";
        }

        gradeCorrectionRepository.deleteById(id);
        return "Grade correction deleted successfully";
    }
}
