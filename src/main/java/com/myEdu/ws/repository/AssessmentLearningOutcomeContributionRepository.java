package com.myEdu.ws.repository;

import com.myEdu.ws.model.Assessment;
import com.myEdu.ws.model.AssessmentLearningOutcomeContribution;
import com.myEdu.ws.model.LearningOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssessmentLearningOutcomeContributionRepository
        extends JpaRepository<AssessmentLearningOutcomeContribution, Long> {

    Optional<AssessmentLearningOutcomeContribution> findByAssessmentAndLearningOutcome(
            Assessment assessment, LearningOutcome learningOutcome);
   Optional<AssessmentLearningOutcomeContribution>  findByLearningOutcomeIdAndAssessmentAssessmentId(Long learningOutcomeId, Long assessmentId);

    List<AssessmentLearningOutcomeContribution> findByLearningOutcome(LearningOutcome learningOutcome);

    @Modifying
    @Query("DELETE FROM AssessmentLearningOutcomeContribution WHERE learningOutcome.id = :learningOutcomeId")
    void deleteAllByLearningOutcome(@Param("learningOutcomeId") long learningOutcomeId);

}
