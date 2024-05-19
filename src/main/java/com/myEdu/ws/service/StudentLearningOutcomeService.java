package com.myEdu.ws.service;

import com.myEdu.ws.dto.StudentLearningOutcomeRequest;
import com.myEdu.ws.model.*;
import com.myEdu.ws.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentLearningOutcomeService {

    private final StudentLearningOutcomeRepository studentLearningOutcomeRepository;
    private final StudentRepository studentRepository;
    private final LearningOutcomeRepository learningOutcomeRepository;
    private final AssessmentLearningOutcomeContributionRepository assessmentLearningOutcomeContributionRepository;
    private final AssessmentRepository assessmentRepository;
    private final StudentAssessmentRepository studentAssessmentRepository;

    public String createStudentLearningOutcome(Long userId, Long learningOutcomeId) {
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + userId));
        LearningOutcome learningOutcome = learningOutcomeRepository.findById(learningOutcomeId)
                .orElseThrow(() -> new RuntimeException("LearningOutcome not found with id: " + learningOutcomeId));

        StudentLearningOutcome studentLearningOutcome = new StudentLearningOutcome();
        studentLearningOutcome.setStudent(student);
        studentLearningOutcome.setLearningOutcome(learningOutcome);
        double levelOfProvision = calculateLevelOfProvision(student, learningOutcome, studentLearningOutcome);
        studentLearningOutcome.setLevelOfProvision(levelOfProvision);
        studentLearningOutcomeRepository.save(studentLearningOutcome);

        return "Ok";
    }

    public String updateStudentLearningOutcome(Long userId, Long learningOutcomeId) {
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + userId));
        LearningOutcome learningOutcome = learningOutcomeRepository.findById(learningOutcomeId)
                .orElseThrow(() -> new RuntimeException("LearningOutcome not found with id: " + learningOutcomeId));

        StudentLearningOutcome studentLearningOutcome = studentLearningOutcomeRepository.findByStudentUserIdAndLearningOutcomeId(userId, learningOutcomeId);
        double levelOfProvision = calculateLevelOfProvision(student, learningOutcome, studentLearningOutcome);
        studentLearningOutcome.setLevelOfProvision(levelOfProvision);
        studentLearningOutcomeRepository.save(studentLearningOutcome);

        return "Ok";
    }

    public double calculateLevelOfProvision(Student student, LearningOutcome learningOutcome, StudentLearningOutcome studentLearningOutcome) {
        List<AssessmentLearningOutcomeContribution> mappings = assessmentLearningOutcomeContributionRepository.findByLearningOutcome(learningOutcome);
        double scoreSum = 0.0;
        for (AssessmentLearningOutcomeContribution mapping : mappings) {
            Assessment assessment = mapping.getAssessment();
            GeneralAssessment generalAssessment = assessmentRepository.findGeneralAssessmentByAssessmentId(assessment.getAssessmentId());
            double X = calculateTotalContribution(generalAssessment);
            double Y = mapping.getContribution();
            double Z = generalAssessment.getTotalContribution();
            double contributionPercentage = (assessment.getContribution() / X) * Y * (Z / 100);
            StudentAssessment studentAssessment = studentAssessmentRepository.findByStudentUserIdAndAssessmentAssessmentId(student.getUserId(), assessment.getAssessmentId());
            double E = studentAssessment.getGrade();
            double F = assessment.getContribution();
            double scoreToAdd = E * contributionPercentage / F;

            scoreSum += scoreToAdd;
        }

        studentLearningOutcome.setScoreSum(scoreSum);
        return scoreSum / learningOutcome.getAssessmentSum() * 100;
    }

    private double calculateTotalContribution(GeneralAssessment generalAssessment) {
        double totalContribution = 0.0;
        List<Assessment> assessments = assessmentRepository.findAssessmentsByGeneralAssessmentId(generalAssessment.getGeneralAssesmentId());
        for (Assessment assessment : assessments) {
            totalContribution += assessment.getContribution();
        }
        return totalContribution;
    }

    public List<StudentLearningOutcome> getByUserIdAndLearningOutcomeIds(Long userId, List<Long> learningOutcomeIds) {
        return studentLearningOutcomeRepository.findByStudentUserIdAndLearningOutcomeIdIn(userId, learningOutcomeIds);
    }
}
