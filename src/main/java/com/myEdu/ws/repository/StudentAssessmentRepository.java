package com.myEdu.ws.repository;

import com.myEdu.ws.model.Assessment;
import com.myEdu.ws.model.LearningOutcomeProgramOutcome;
import com.myEdu.ws.model.Student;
import com.myEdu.ws.model.StudentAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentAssessmentRepository extends JpaRepository<StudentAssessment, Long> {

    @Modifying
    @Query("DELETE FROM StudentAssessment WHERE assessment.assessmentId = :assessmentId")
    void deleteAllByAssessment(@Param("assessmentId") long assessmentId);

    void deleteByStudentUserIdAndAssessmentAssessmentId(Long userId, Long assessmentId);

    @Modifying
    @Query("DELETE FROM StudentAssessment WHERE assessment.assessmentId = :assessmentId AND student.userId = :studentId")
    void deleteStudentAssessment(@Param("studentId")Long studentId, @Param("assessmentId")Long assessmentId);


    StudentAssessment findByStudentUserIdAndAssessmentAssessmentId(Long userId, Long assessmentId);

    Optional<StudentAssessment> findByAssessmentAssessmentIdAndStudentUserId(Long assessmentId, Long userId);

    List<StudentAssessment> findByAssessment_AssessmentId(Long assessmentId);

}
