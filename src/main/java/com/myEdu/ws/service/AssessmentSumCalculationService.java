package com.myEdu.ws.service;

import com.myEdu.ws.model.Assessment;
import com.myEdu.ws.model.AssessmentLearningOutcomeContribution;
import com.myEdu.ws.model.GeneralAssessment;
import com.myEdu.ws.model.LearningOutcome;
import com.myEdu.ws.repository.AssessmentRepository;
import com.myEdu.ws.repository.AssessmentLearningOutcomeContributionRepository;
import com.myEdu.ws.repository.GeneralAssessmentRepository;
import com.myEdu.ws.repository.LearningOutcomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentSumCalculationService {

    private final AssessmentLearningOutcomeContributionRepository assessmentLearningOutcomeContributionRepository;
    private final AssessmentRepository assessmentRepository;
    private final GeneralAssessmentRepository generalAssessmentRepository;
    private final LearningOutcomeRepository learningOutcomeRepository;

    public void calculateAndSetAssessmentSum() {
        List<LearningOutcome> learningOutcomes = learningOutcomeRepository.findAll();
        for (LearningOutcome learningOutcome : learningOutcomes) {
            List<AssessmentLearningOutcomeContribution> mappings = assessmentLearningOutcomeContributionRepository.findByLearningOutcome(learningOutcome);
            double assessmentSum = 0.0;
            for (AssessmentLearningOutcomeContribution mapping : mappings) {
                Assessment assessment = mapping.getAssessment();
                GeneralAssessment generalAssessment = assessment.getGeneralAssessment();
                double X = calculateTotalContribution(generalAssessment);
                double Y = mapping.getContribution();
                double Z = generalAssessment.getTotalContribution();
                double contributionPercentage = (assessment.getContribution() / X) * Y * (Z / 100);
                assessmentSum += contributionPercentage;
            }
            learningOutcome.setAssessmentSum(assessmentSum);
            learningOutcomeRepository.save(learningOutcome);
        }
    }

    private double calculateTotalContribution(GeneralAssessment generalAssessment) {
        double totalContribution = 0.0;
        List<Assessment> assessments = generalAssessment.getAssessments();
        for (Assessment assessment : assessments) {
            totalContribution += assessment.getContribution();
        }
        return totalContribution;
    }

    public void calculateAndSetScoreSumForLearningOutcome(LearningOutcome learningOutcome) {
        List<AssessmentLearningOutcomeContribution> mappings = assessmentLearningOutcomeContributionRepository.findByLearningOutcome(learningOutcome);
        double scoreSum = 0.0;
        for (AssessmentLearningOutcomeContribution mapping : mappings) {
            Assessment assessment = mapping.getAssessment();
            GeneralAssessment generalAssessment = assessment.getGeneralAssessment();
            double X = calculateTotalContribution(generalAssessment);
            double Y = mapping.getContribution();
            double Z = generalAssessment.getTotalContribution();
            double contributionPercentage = (assessment.getContribution() / X) * Y * (Z / 100);
            double E = assessment.getAverageGrade();
            double F = assessment.getContribution();
            double scoreToAdd = E * contributionPercentage / F;

            scoreSum += scoreToAdd;
        }
        learningOutcome.setScoreSum(scoreSum);
        learningOutcomeRepository.save(learningOutcome);
    }

}
