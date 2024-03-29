package com.myEdu.ws.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Assessment_LearningOutcome_Contribution")
@NoArgsConstructor
public class AssessmentLearningOutcomeContribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    @ManyToOne
    @JoinColumn(name = "learning_outcome_id")
    private LearningOutcome learningOutcome;

    @Column(nullable = false)
    private Double contribution;

    public AssessmentLearningOutcomeContribution(Assessment assessment, LearningOutcome learningOutcome, Double contribution) {
        this.assessment = assessment;
        this.learningOutcome = learningOutcome;
        this.contribution = contribution;
    }

    public void setContribution(Double contribution) {
        this.contribution = contribution;
    }

}
