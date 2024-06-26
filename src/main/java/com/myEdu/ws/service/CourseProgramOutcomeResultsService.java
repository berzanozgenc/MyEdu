package com.myEdu.ws.service;

import com.myEdu.ws.model.*;
import com.myEdu.ws.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseProgramOutcomeResultsService {

    @Autowired
    private ProgramOutcomeRepository programOutcomeRepository;

    @Autowired
    private ProgramOutcomeCalculationService programOutcomeCalculationService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LearningOutcomeProgramOutcomeRepository learningOutcomeProgramOutcomeRepository;

    @Autowired
    private CourseProgramOutcomeResultsRepository courseProgramOutcomeResultsRepository;

    public ResponseEntity<String> createCourseProgramOutcomeResults(Course course) {
        calculateAndSetProgramOutcomeTarget(course);
        return new ResponseEntity<>("Başarıyla oluşturuldu", HttpStatus.CREATED);
    }

    public void calculateAndSetProgramOutcomeTarget(Course course) {
        Department department = course.getDepartment();
        Long id = department.getId();
        List<ProgramOutcome> programOutcomes = programOutcomeRepository.findByDepartmentId(id);
        for (ProgramOutcome programOutcome : programOutcomes) {
            Optional<CourseProgramOutcomeResults> optionalResults = courseProgramOutcomeResultsRepository.findByCourseAndProgramOutcome(course, programOutcome);
            CourseProgramOutcomeResults courseProgramOutcomeResults;
            if (optionalResults.isPresent()) {
                courseProgramOutcomeResults = optionalResults.get();
            } else {
                courseProgramOutcomeResults = new CourseProgramOutcomeResults();
                courseProgramOutcomeResults.setCourse(course);
                courseProgramOutcomeResults.setProgramOutcome(programOutcome);
            }
            double target = programOutcomeCalculationService.calculateProgramOutcomeTarget(programOutcome);
            courseProgramOutcomeResults.setTarget(target);
            calculateAndSetAssessmentValueForProgramOutcome(courseProgramOutcomeResults, programOutcome, course);
        }
    }

    public void calculateAndSetAssessmentValueForProgramOutcome(CourseProgramOutcomeResults courseProgramOutcomeResults, ProgramOutcome programOutcome, Course course) {
        List<LearningOutcomeProgramOutcome> mappings = learningOutcomeProgramOutcomeRepository.findByProgramOutcomeAndCourse(programOutcome, course);
        double assessmentValue = 0.0;
        for (LearningOutcomeProgramOutcome mapping : mappings) {
            LearningOutcome learningOutcome = mapping.getLearningOutcome();
            double contribution = mapping.getContribution();
            double learningOutcomeAssessmentSum = learningOutcome.getAssessmentSum();
            double valueToAdd = (learningOutcomeAssessmentSum * contribution) / 100;
            assessmentValue += valueToAdd;
        }
        courseProgramOutcomeResults.setAssessmentValue(assessmentValue);
        calculateAndSetScoreAndLevelOfProvisionForProgramOutcome(courseProgramOutcomeResults, programOutcome, assessmentValue, course);
    }

    public void calculateAndSetScoreAndLevelOfProvisionForProgramOutcome(CourseProgramOutcomeResults courseProgramOutcomeResults, ProgramOutcome programOutcome, double assessmentValue, Course course) {
        List<LearningOutcomeProgramOutcome> mappings = learningOutcomeProgramOutcomeRepository.findByProgramOutcomeAndCourse(programOutcome, course);
        double score = 0.0;
        for (LearningOutcomeProgramOutcome mapping : mappings) {
            LearningOutcome learningOutcome = mapping.getLearningOutcome();
            double contribution = mapping.getContribution();
            double learningOutcomeScoreSum = learningOutcome.getScoreSum();
            double valueToAdd = (learningOutcomeScoreSum * contribution) / 100;
            score += valueToAdd;
        }
        double levelOfProvision = score / assessmentValue * 100;
        courseProgramOutcomeResults.setLevelOfProvision(levelOfProvision);
        courseProgramOutcomeResults.setScore(score);
        courseProgramOutcomeResultsRepository.save(courseProgramOutcomeResults);
    }

    public List<CourseProgramOutcomeResults> getCourseProgramOutcomeResultsByCourse(Long courseId) {
        return courseProgramOutcomeResultsRepository.findByCourseCourseId(courseId);
    }

}
