package com.myEdu.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assessmentId;

    private String name;

    private Double contribution;

    @Column()
    private Double averageGrade;

    @ManyToOne
    @JoinColumn(name = "generalAssesmentId")
    @JsonIgnore
    private GeneralAssessment generalAssessment;

    @PrePersist
    public void generateAssessmentName() {
        if (generalAssessment != null && name == null) {
            String baseName;
            String baseNameBeforeLowerCase;

            String lowerCaseGeneralAssessmentName = generalAssessment.getName().toLowerCase();
            baseNameBeforeLowerCase = generalAssessment.getName();

            if (lowerCaseGeneralAssessmentName.equals("vize") ||
                    lowerCaseGeneralAssessmentName.equals("final") ||
                    lowerCaseGeneralAssessmentName.equals("midterm")) {
                baseName = baseNameBeforeLowerCase;
            } else {
                baseName = baseNameBeforeLowerCase;
            }

            List<Assessment> assessments = generalAssessment.getAssessments();
            int newNumber = assessments.size() + 1;
            name = baseName;
        }
    }

    @ManyToMany
    @JoinTable(
            name = "Assessment_LearningOutcome_Contribution",
            joinColumns = @JoinColumn(name = "assessment_id"),
            inverseJoinColumns = @JoinColumn(name = "learning_outcome_id")
    )
    private List<LearningOutcome> learningOutcomes;

    @OneToMany(mappedBy = "assessment", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<StudentAssessment> studentAssessment;

}
