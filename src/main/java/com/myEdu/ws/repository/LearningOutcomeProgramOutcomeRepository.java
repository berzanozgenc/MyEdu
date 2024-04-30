package com.myEdu.ws.repository;

import com.myEdu.ws.model.LearningOutcomeProgramOutcome;
import com.myEdu.ws.model.ProgramOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningOutcomeProgramOutcomeRepository extends JpaRepository  <LearningOutcomeProgramOutcome, Long> {
    LearningOutcomeProgramOutcome findByLearningOutcomeIdAndProgramOutcomeId(Long learningOutcomeId, Long programOutcomeId);
    List<LearningOutcomeProgramOutcome> findByProgramOutcome(ProgramOutcome programOutcome);

}
