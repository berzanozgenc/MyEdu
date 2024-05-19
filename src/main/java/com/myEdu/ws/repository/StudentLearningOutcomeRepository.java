package com.myEdu.ws.repository;

import com.myEdu.ws.model.StudentAssessment;
import com.myEdu.ws.model.StudentLearningOutcome;
import com.myEdu.ws.model.StudentProgramOutcome;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentLearningOutcomeRepository extends JpaRepository<StudentLearningOutcome, Long> {

    StudentLearningOutcome findByStudentUserIdAndLearningOutcomeId(Long userId, Long learningId);

    List<StudentLearningOutcome> findByStudentUserIdAndLearningOutcomeIdIn(Long userId, List<Long> learningOutcomeIds);


}
