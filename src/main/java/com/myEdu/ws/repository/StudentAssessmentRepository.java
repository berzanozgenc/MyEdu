package com.myEdu.ws.repository;

import com.myEdu.ws.model.StudentAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentAssessmentRepository extends JpaRepository<StudentAssessment, Long> {
    Optional<StudentAssessment> findByStudentUserIdAndAssessmentAssessmentId(Long userId, Long assessmentId);

}