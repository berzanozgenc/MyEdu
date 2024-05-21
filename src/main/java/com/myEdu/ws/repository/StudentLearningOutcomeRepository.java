package com.myEdu.ws.repository;

import com.myEdu.ws.model.StudentAssessment;
import com.myEdu.ws.model.StudentLearningOutcome;
import com.myEdu.ws.model.StudentProgramOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentLearningOutcomeRepository extends JpaRepository<StudentLearningOutcome, Long> {

    StudentLearningOutcome findByStudentUserIdAndLearningOutcomeId(Long userId, Long learningId);

    List<StudentLearningOutcome> findByStudentUserIdAndLearningOutcomeIdIn(Long userId, List<Long> learningOutcomeIds);

    @Modifying
    @Query("DELETE FROM StudentLearningOutcome WHERE learningOutcome.id = :learningOutcomeId")
    void deleteAllByLearningOutcome(@Param("learningOutcomeId") long learningOutcomeId);

}
