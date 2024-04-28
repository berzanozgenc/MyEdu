package com.myEdu.ws.repository;

import com.myEdu.ws.model.Assessment;
import com.myEdu.ws.model.AssessmentLearningOutcomeContribution;
import com.myEdu.ws.model.LearningOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssessmentLearningOutcomeContributionRepository
        extends JpaRepository<AssessmentLearningOutcomeContribution, Long> {

    Optional<AssessmentLearningOutcomeContribution> findByAssessmentAndLearningOutcome(
            Assessment assessment, LearningOutcome learningOutcome);
    AssessmentLearningOutcomeContribution findByLearningOutcomeIdAndAssessmentAssessmentId(Long learningOutcomeId, Long assessmentId);
}
