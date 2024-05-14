package com.myEdu.ws.controller;

import com.myEdu.ws.dto.LearningOutcomeRequest;
import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.LearningOutcome;
import com.myEdu.ws.repository.CourseRepository;
import com.myEdu.ws.repository.LearningOutcomeProgramOutcomeRepository;
import com.myEdu.ws.repository.LearningOutcomeRepository;
import com.myEdu.ws.service.AssessmentSumCalculationService;
import com.myEdu.ws.service.LearningOutcomeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/learningOutcomes")
public class LearningOutcomeController {

    @Autowired
    private LearningOutcomeService learningOutcomeService;

    @Autowired
    private AssessmentSumCalculationService assessmentSumCalculationService;

    @Autowired
    private LearningOutcomeRepository learningOutcomeRepository;

    @Autowired
    private LearningOutcomeProgramOutcomeRepository learningOutcomeProgramOutcomeRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<List<LearningOutcome>> getAllLearningOutcomes() {
        List<LearningOutcome> learningOutcomes = learningOutcomeService.getAllLearningOutcomes();
        return new ResponseEntity<>(learningOutcomes, HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<LearningOutcome>> getLearningOutcomesByCourseId(@PathVariable Long courseId) {
        List<LearningOutcome> learningOutcomes = learningOutcomeService.getByCourseId(courseId);
        return ResponseEntity.ok().body(learningOutcomes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LearningOutcome> getLearningOutcomeById(@PathVariable Long id) {
        Optional<LearningOutcome> learningOutcome = learningOutcomeService.getLearningOutcomeById(id);
        return learningOutcome.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create-learningOutcome")
    public ResponseEntity<LearningOutcome> createLearningOutcome(@RequestBody LearningOutcomeRequest request) {
        LearningOutcome createdLearningOutcome = learningOutcomeService.createLearningOutcome(request);
        return new ResponseEntity<>(createdLearningOutcome, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LearningOutcome> updateLearningOutcome(@PathVariable Long id,
                                                                 @RequestBody LearningOutcome updatedLearningOutcome) {
        LearningOutcome learningOutcome = learningOutcomeService.updateLearningOutcome(id, updatedLearningOutcome);
        return new ResponseEntity<>(learningOutcome, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteLearningOutcome(@PathVariable Long id) {
        learningOutcomeProgramOutcomeRepository.deleteAllByLearningOutcome(id);
        learningOutcomeService.deleteLearningOutcome(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("course/{id}/calculate-and-set-assessment-sum")
    public ResponseEntity<String> calculateAndSetAssessmentSumForLearningOutcome(@PathVariable Long id) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }
        assessmentSumCalculationService.calculateAndSetAssessmentSum(id); // Yeni yöntemi kullanarak assessmentSum hesaplayın ve kaydedin
        return ResponseEntity.ok("Assessment sum calculated and set successfully for Course with ID: " + id);
    }

    @PostMapping("course/{id}/calculate-and-set-score-sum")
    public ResponseEntity<String> calculateAndSetScoreSumForLearningOutcome(@PathVariable Long id) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }
        assessmentSumCalculationService.calculateAndSetScoreSumAndLevelOfProvisionForLearningOutcome(id);
        return ResponseEntity.ok("Score sum calculated and set successfully for Course with ID: " + id);
    }

}
