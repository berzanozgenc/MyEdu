package com.myEdu.ws.repository;

import com.myEdu.ws.model.Assessment;
import com.myEdu.ws.model.GeneralAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    List<Assessment> findByGeneralAssessment(GeneralAssessment generalAssessment);
    List<Assessment> findByGeneralAssessment_GeneralAssesmentId(Long generalAssesmentId);
    @Query("SELECT a FROM Assessment a WHERE a.generalAssessment.generalAssesmentId = :generalAssessmentId")
    List<Assessment> findAssessmentsByGeneralAssessmentId(@Param("generalAssessmentId") Long generalAssessmentId);
    @Query("SELECT a.generalAssessment FROM Assessment a WHERE a.id = :assessmentId")
    GeneralAssessment findGeneralAssessmentByAssessmentId(@Param("assessmentId") Long assessmentId);

    @Modifying
    @Query("DELETE FROM Assessment WHERE generalAssessment.generalAssesmentId = :generalAssesmentId")
    void deleteAllByGeneralAssessment(@Param("generalAssesmentId") long generalAssesmentId);

}
