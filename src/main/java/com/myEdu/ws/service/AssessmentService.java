package com.myEdu.ws.service;

import com.myEdu.ws.dto.AssessmentRequest;
import com.myEdu.ws.model.Assessment;
import com.myEdu.ws.model.GeneralAssessment;
import com.myEdu.ws.repository.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.repository.CrudRepository;


import java.util.List;
import java.util.Optional;

@Service
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final GeneralAssessmentService generalAssessmentService;

    @Autowired
    public AssessmentService(AssessmentRepository assessmentRepository, GeneralAssessmentService generalAssessmentService) {
        this.assessmentRepository = assessmentRepository;
        this.generalAssessmentService = generalAssessmentService;
    }

    public List<Assessment> getAllAssessments() {
        return assessmentRepository.findAll();
    }

    public Optional<Assessment> findAssessmentById(Long id) {
        return assessmentRepository.findById(id);
    }

    public Assessment createAssessment(Assessment assessment) {
        return assessmentRepository.save(assessment);
    }

    public Double getTotalContributionByGeneralAssessment(GeneralAssessment generalAssessment) {

        List<Assessment> assessments = assessmentRepository.findByGeneralAssessment(generalAssessment);

        Double totalContribution = assessments.stream()
                .mapToDouble(Assessment::getContribution)
                .sum();

        return totalContribution;
    }

    public void updateAssessment(Long id, AssessmentRequest request) {
        Optional<Assessment> optionalAssessment = assessmentRepository.findById(id);

        if (optionalAssessment.isPresent()) {
            Assessment assessment = optionalAssessment.get();
            assessment.setName(request.getName());
            assessment.setContribution(request.getContribution());
            assessment.setAverageGrade(request.getAverageGrade());

            assessmentRepository.save(assessment);
        } else {
            throw new IllegalArgumentException("Assessment with the provided id not found.");
        }
    }

    public List<Assessment> getAssessmentsByGeneralAssessmentId(Long generalAssessmentId) {
        return assessmentRepository.findByGeneralAssessment_GeneralAssesmentId(generalAssessmentId);
    }
    public void updateAssessmentContribution(Long id, Double newContribution) {
        Optional<Assessment> optionalAssessment = assessmentRepository.findById(id);

        if (optionalAssessment.isPresent()) {
            Assessment assessment = optionalAssessment.get();
            Double currentContribution = assessment.getContribution();
            Double generalAssessmentTotalContribution = getTotalContributionByGeneralAssessment(assessment.getGeneralAssessment());

            if ((generalAssessmentTotalContribution - currentContribution + newContribution) <= 1000) {
                assessment.setContribution(newContribution);
                assessmentRepository.save(assessment);
            } else {
                throw new IllegalArgumentException("Total contribution cannot exceed 100%");
            }
        } else {
            throw new IllegalArgumentException("Assessment with the provided id not found.");
        }
    }

    public void deleteAssessmentById(Long id) {
        assessmentRepository.deleteById(id);
    }

}
