package college.edu.gradefix.domain;

import jakarta.persistence.*;
import java.util.UUID;
import java.time.LocalDate;

@Entity
@Table(name="grade_correction")
public class GradeCorrection {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String assessmentName;
    private String comment;
    private int newMark;
    private LocalDate correctionDate;

    @ManyToOne
    @JoinColumn(name = "grade_claim_id")
    private GradeClaim gradeClaim;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getNewMark() {
        return newMark;
    }

    public void setNewMark(int newMark) {
        this.newMark = newMark;
    }

    public LocalDate getCorrectionDate() {
        return correctionDate;
    }

    public void setCorrectionDate(LocalDate correctionDate) {
        this.correctionDate = correctionDate;
    }

    public GradeClaim getGradeClaim() {
        return gradeClaim;
    }

    public void setGradeClaim(GradeClaim gradeClaim) {
        this.gradeClaim = gradeClaim;
    }
    
}