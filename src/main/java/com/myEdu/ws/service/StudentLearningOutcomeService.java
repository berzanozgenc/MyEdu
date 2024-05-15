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

    @Autowired
    private final StudentLearningOutcomeRepository studentLearningOutcomeRepository;

    @Autowired
    private final StudentRepository studentRepository;

    @Autowired
    private final LearningOutcomeRepository learningOutcomeRepository ;

    @Autowired
    private final AssessmentLearningOutcomeContributionRepository assessmentLearningOutcomeContributionRepository;

    @Autowired
    private  final AssessmentRepository assessmentRepository;

    @Autowired
    private  final StudentAssessmentRepository studentAssessmentRepository;

    public String createStudentLearningOutcome(Long userId, Long learningOutcomeId) {
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + userId));
        Optional<LearningOutcome> learningOutcomeOpt = learningOutcomeRepository.findById(learningOutcomeId);
        LearningOutcome learningOutcome = learningOutcomeOpt.get();
            double levelOfProvision = 0;
            StudentLearningOutcome studentLearningOutcome = new StudentLearningOutcome();
            studentLearningOutcome.setStudent(student);
            studentLearningOutcome.setLearningOutcome(learningOutcome);
            levelOfProvision = calculateLevelOfProvision(student,learningOutcome);
            studentLearningOutcome.setLevelOfProvision(levelOfProvision);
            studentLearningOutcomeRepository.save(studentLearningOutcome);

        return ("Ok");

    }

    public String updateStudentLearningOutcome(Long userId, Long learningOutcomeId) {
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + userId));
        Optional<LearningOutcome> learningOutcomeOpt = learningOutcomeRepository.findById(learningOutcomeId);
        LearningOutcome learningOutcome = learningOutcomeOpt.get();
        StudentLearningOutcome studentLearningOutcome = studentLearningOutcomeRepository.findByStudentUserIdAndLearningOutcomeId(userId, learningOutcomeId);
        double levelOfProvision = 0;
        studentLearningOutcome.setStudent(student);
        studentLearningOutcome.setLearningOutcome(learningOutcome);
        levelOfProvision = calculateLevelOfProvision(student,learningOutcome);
        studentLearningOutcome.setLevelOfProvision(levelOfProvision);
        studentLearningOutcomeRepository.save(studentLearningOutcome);

        return ("Ok");

    }

    public double calculateLevelOfProvision(Student student, LearningOutcome learningOutcome){
            List<AssessmentLearningOutcomeContribution> mappings = assessmentLearningOutcomeContributionRepository.findByLearningOutcome(learningOutcome);
            double scoreSum = 0.0;
            for (AssessmentLearningOutcomeContribution mapping : mappings) {
                Assessment assessment = mapping.getAssessment();
                GeneralAssessment generalAssessment = assessmentRepository.findGeneralAssessmentByAssessmentId(assessment.getAssessmentId());
                double X = calculateTotalContribution(generalAssessment);
                double Y = mapping.getContribution();
                double Z = generalAssessment.getTotalContribution();
                double contributionPercentage = (assessment.getContribution() / X) * Y * (Z / 100);
                StudentAssessment studentAssessment = studentAssessmentRepository.findByStudentUserIdAndAssessmentAssessmentId(student.getUserId(),assessment.getAssessmentId());
                double E = studentAssessment.getGrade();
                double F = assessment.getContribution();
                double scoreToAdd = E * contributionPercentage / F;

                scoreSum += scoreToAdd;
            }
        return scoreSum / learningOutcome.getAssessmentSum() * 100;

    }

    private double calculateTotalContribution(GeneralAssessment generalAssessment) {
        double totalContribution = 0.0;
        List<Assessment> assessments =  assessmentRepository.findAssessmentsByGeneralAssessmentId(generalAssessment.getGeneralAssesmentId());
        for (Assessment assessment : assessments) {
            totalContribution += assessment.getContribution();
        }
        return totalContribution;
    }

}
