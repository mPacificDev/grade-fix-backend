package college.edu.gradefix.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import college.edu.gradefix.domain.ClaimStatus;
import college.edu.gradefix.domain.GradeClaim;
import college.edu.gradefix.repository.GradeClaimRepository;
import college.edu.gradefix.repository.GradeCorrectionRepository;

@Service
public class GradeClaimService {
    private final GradeClaimRepository gradeClaimRepository;
    private final GradeCorrectionRepository gradeCorrectionRepository;

    public GradeClaimService(
            GradeClaimRepository gradeClaimRepository,
            GradeCorrectionRepository gradeCorrectionRepository) {
        this.gradeClaimRepository = gradeClaimRepository;
        this.gradeCorrectionRepository = gradeCorrectionRepository;
    }

    public List<GradeClaim> claims() {
        return gradeClaimRepository.findAll();
    }

    public Optional<GradeClaim> claim(UUID id) {
        return gradeClaimRepository.findById(id);
    }

    public List<GradeClaim> byStatus(ClaimStatus status) {
        return gradeClaimRepository.findByStatusOrderByClaimDateAsc(status);
    }

    public List<GradeClaim> between(LocalDate start, LocalDate end) {
        return gradeClaimRepository.findByClaimDateBetweenOrderByClaimDateAsc(start, end);
    }

    public List<Object[]> stats() {
        return gradeClaimRepository.statsByStatus();
    }

    @Transactional
    public String saveClaim(GradeClaim claim) {
        if (claim.getClaimDate().isBefore(LocalDate.now())) {
            return "Grade claim date cannot be in the past";
        }
        if (gradeClaimRepository.existsByLecturerAndClaimDateAndStatusNot(
                claim.getLecturer(), claim.getClaimDate(), ClaimStatus.DISMISSED)) {
            return "Lecturer already has a claim to investigate on that date";
        }
        claim.setStatus(ClaimStatus.SUBMITTED);
        gradeClaimRepository.save(claim);
        return "Grade claim saved successfully";
    }

    public Optional<GradeClaim> updateClaim(UUID id, GradeClaim updatedClaim) {
        Optional<GradeClaim> existingClaim = gradeClaimRepository.findById(id);
        if (existingClaim.isEmpty()) {
            return Optional.empty();
        }

        updatedClaim.setId(id);
        updatedClaim.setStatus(existingClaim.get().getStatus());
        GradeClaim savedClaim = gradeClaimRepository.save(updatedClaim);
        return Optional.of(savedClaim);
    }

    public String changeStatus(UUID id, ClaimStatus nextStatus) {
        Optional<GradeClaim> claimResult = gradeClaimRepository.findById(id);
        if (claimResult.isEmpty()) {
            return "The grade claim with that id does not exist";
        }
        GradeClaim claim = claimResult.get();
        ClaimStatus currentStatus = claim.getStatus();
        boolean allowed = (currentStatus == ClaimStatus.SUBMITTED
                && (nextStatus == ClaimStatus.INVESTIGATING || nextStatus == ClaimStatus.DISMISSED))
                || (currentStatus == ClaimStatus.INVESTIGATING
                        && (nextStatus == ClaimStatus.RESOLVED || nextStatus == ClaimStatus.DISMISSED));
        if (!allowed) {
            return "Cannot change status from " + currentStatus + " to " + nextStatus;
        }
        claim.setStatus(nextStatus);
        gradeClaimRepository.save(claim);
        return "Grade claim status updated successfully";
    }

    @Transactional
    public String cancelDay(UUID lecturerId, LocalDate date) {
        return gradeClaimRepository.cancelClaims(lecturerId, date) + " grade claims cancelled";
    }

    public String deleteClaim(UUID id) {
        Optional<GradeClaim> claimResult = gradeClaimRepository.findById(id);
        if (claimResult.isEmpty()) {
            return "The grade claim with that id does not exist";
        }
        if (gradeCorrectionRepository.existsByGradeClaim(claimResult.get())) {
            return "That grade claim has grade corrections, delete them first";
        }
        gradeClaimRepository.delete(claimResult.get());
        return "Grade claim deleted successfully";
    }

    public Page<GradeClaim> page(Pageable pageable) {
        return gradeClaimRepository.findAll(pageable);
    }
}
