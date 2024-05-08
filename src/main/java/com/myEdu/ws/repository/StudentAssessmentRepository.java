package com.myEdu.ws.repository;

import com.myEdu.ws.model.LearningOutcomeProgramOutcome;
import com.myEdu.ws.model.StudentAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentAssessmentRepository extends JpaRepository<StudentAssessment, Long> {

    void deleteByStudentUserIdAndAssessmentAssessmentId(Long userId, Long assessmentId);

    StudentAssessment findByStudentUserIdAndAssessmentAssessmentId(Long userId, Long assessmentId);

    Optional<StudentAssessment> findByAssessmentAssessmentIdAndStudentUserId(Long assessmentId, Long userId);

    List<StudentAssessment> findByAssessment_AssessmentId(Long assessmentId);

}
