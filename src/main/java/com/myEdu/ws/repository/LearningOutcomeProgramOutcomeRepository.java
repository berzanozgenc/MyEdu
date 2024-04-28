package com.myEdu.ws.repository;

import com.myEdu.ws.model.LearningOutcomeProgramOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningOutcomeProgramOutcomeRepository extends JpaRepository  <LearningOutcomeProgramOutcome, Long> {
    LearningOutcomeProgramOutcome findByLearningOutcomeIdAndProgramOutcomeId(Long learningOutcomeId, Long programOutcomeId);
}
