package college.edu.gradefix.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import college.edu.gradefix.domain.ClaimStatus;
import college.edu.gradefix.domain.GradeClaim;
import college.edu.gradefix.service.GradeClaimService;

@RestController
public class GradeClaimController {
    private final GradeClaimService gradeClaimService;

    public GradeClaimController(GradeClaimService gradeClaimService) {
        this.gradeClaimService = gradeClaimService;
    }

    @PostMapping("/api/grade-claims/save")
    public ResponseEntity<String> saveClaim(@RequestBody GradeClaim claim) {
        String message = gradeClaimService.saveClaim(claim);
        if (message.equals("Grade claim saved successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }
        return GradefixResponse.reply(message);
    }

    @GetMapping("/api/grade-claims/all")
    public List<GradeClaim> claims() {
        return gradeClaimService.claims();
    }

    @GetMapping("/api/grade-claims/{id}")
    public ResponseEntity<?> claim(@PathVariable UUID id) {
        Optional<GradeClaim> claim = gradeClaimService.claim(id);
        if (claim.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The grade claim with that id does not exist");
        }
        return ResponseEntity.ok(claim.get());
    }

    @PutMapping("/api/grade-claims/update/{id}")
    public ResponseEntity<?> updateClaim(@PathVariable UUID id, @RequestBody GradeClaim claim) {
        Optional<GradeClaim> updatedClaim = gradeClaimService.updateClaim(id, claim);
        if (updatedClaim.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The grade claim with that id does not exist");
        }
        return ResponseEntity.ok(updatedClaim.get());
    }

    @DeleteMapping("/api/grade-claims/delete/{id}")
    public ResponseEntity<String> deleteClaim(@PathVariable UUID id) {
        String message = gradeClaimService.deleteClaim(id);
        if (message.equals("The grade claim with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        if (message.startsWith("That grade claim has grade corrections")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping("/api/grade-claims/by-status")
    public List<GradeClaim> byStatus(@RequestParam ClaimStatus status) {
        return gradeClaimService.byStatus(status);
    }

    @GetMapping("/api/grade-claims/between")
    public List<GradeClaim> between(@RequestParam String start, @RequestParam String end) {
        return gradeClaimService.between(LocalDate.parse(start), LocalDate.parse(end));
    }

    @PatchMapping("/api/grade-claims/{id}/status")
    public ResponseEntity<String> status(
            @PathVariable UUID id,
            @RequestParam ClaimStatus status) {
        return GradefixResponse.reply(gradeClaimService.changeStatus(id, status));
    }

    @GetMapping("/api/grade-claims/stats/by-status")
    public List<Object[]> stats() {
        return gradeClaimService.stats();
    }

    @PatchMapping("/api/grade-claims/cancel-day")
    public ResponseEntity<String> cancelDay(
            @RequestParam UUID lecturerId,
            @RequestParam String date) {
        return GradefixResponse.reply(
                gradeClaimService.cancelDay(lecturerId, LocalDate.parse(date)));
    }

    @GetMapping("/api/grade-claims/page")
    public Page<GradeClaim> page(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "claimDate,desc") String sort) {
        String[] values = sort.split(",");
        Sort.Direction direction = Sort.Direction.DESC;
        if (values.length > 1 && values[1].equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Sort.Direction.DESC;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, values[0]));
        return gradeClaimService.page(pageable);
    }
}
