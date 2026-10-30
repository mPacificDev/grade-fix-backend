package college.edu.gradefix.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import college.edu.gradefix.domain.GradeCorrection;
import college.edu.gradefix.service.GradeCorrectionService;

@RestController
@RequestMapping("/api/grade-corrections")
public class GradeCorrectionController {
    private final GradeCorrectionService gradeCorrectionService;

    public GradeCorrectionController(GradeCorrectionService gradeCorrectionService) {
        this.gradeCorrectionService = gradeCorrectionService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(
            @RequestParam UUID gradeClaimId,
            @RequestBody GradeCorrection correction) {
        String message = gradeCorrectionService.save(correction, gradeClaimId);
        if (message.equals("Grade correction saved successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } else if (message.equals("The grade claim with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.badRequest().body(message);
        }
    }

    @GetMapping("/student/{regNumber}")
    public List<GradeCorrection> history(@PathVariable String regNumber) {
        return gradeCorrectionService.history(regNumber);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = gradeCorrectionService.delete(id);
        if (message.equals("The grade correction with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }
}
