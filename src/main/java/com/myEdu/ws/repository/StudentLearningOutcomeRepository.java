package com.myEdu.ws.repository;

import com.myEdu.ws.model.StudentAssessment;
import com.myEdu.ws.model.StudentLearningOutcome;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentLearningOutcomeRepository extends JpaRepository<StudentLearningOutcome, Long> {

    StudentLearningOutcome findByStudentUserIdAndLearningOutcomeId(Long userId, Long learningId);

}
