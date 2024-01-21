package com.myEdu.ws.repository;

import com.myEdu.ws.model.Assessment;
import com.myEdu.ws.model.GeneralAssessment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    List<Assessment> findByGeneralAssessment(GeneralAssessment generalAssessment);
}
