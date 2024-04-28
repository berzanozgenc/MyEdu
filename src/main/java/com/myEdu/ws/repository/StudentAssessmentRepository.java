package com.myEdu.ws.repository;

import com.myEdu.ws.model.StudentAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentAssessmentRepository extends JpaRepository<StudentAssessment, Long> {

    void deleteByStudentUserIdAndAssessmentAssessmentId(Long userId, Long assessmentId);

    StudentAssessment findByStudentUserIdAndAssessmentAssessmentId(Long userId, Long assessmentId);
}
