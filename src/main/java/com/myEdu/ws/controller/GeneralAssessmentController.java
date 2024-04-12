package com.myEdu.ws.controller;

import com.myEdu.ws.dto.GeneralAssessmentRequest;
import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.GeneralAssessment;
import com.myEdu.ws.repository.CourseRepository;
import com.myEdu.ws.service.GeneralAssessmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/generalAssesment")
public class GeneralAssessmentController {

    private final GeneralAssessmentService generalAssesmentService;
    private final CourseRepository courseRepository;

    @Autowired
    public GeneralAssessmentController(GeneralAssessmentService generalAssesmentService, CourseRepository courseRepository) {
        this.generalAssesmentService = generalAssesmentService;
        this.courseRepository = courseRepository;
    }

    @PostMapping("/create-generalAssesment")
    public ResponseEntity<Object> addGeneralAssessment(@RequestBody GeneralAssessmentRequest request) {
        try {
            GeneralAssessment addedAssessment = generalAssesmentService.addGeneralAssessment(request.getCourseId(), request.getName(), request.getTotalContribution());
            return ResponseEntity.ok(addedAssessment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{generalAssessmentId}/isQuestionBased")
    public ResponseEntity<Boolean> isGeneralAssessmentQuestionBased(@PathVariable Long generalAssessmentId) {
        boolean isQuestionBased = generalAssesmentService.isGeneralAssessmentQuestionBased(generalAssessmentId);
        return new ResponseEntity<>(isQuestionBased, HttpStatus.OK);
    }

    @PutMapping("/{generalAssessmentId}/toggleQuestionBased")
    public ResponseEntity<String> toggleQuestionBased(@PathVariable Long generalAssessmentId) {
        generalAssesmentService.toggleQuestionBased(generalAssessmentId);
        return new ResponseEntity<>("Question based toggled successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete-generalAssesment/{id}")
    public void deleteGeneralAssesment(@PathVariable("id") long generalAssesmentId) {
        generalAssesmentService.deleteGeneralAssesmentById(generalAssesmentId);
    }

    @GetMapping("/get-generalAssesmentById")
    public GeneralAssessment getGeneralAssesmentById(@PathVariable("id") long generalAssesmentId) {
        return generalAssesmentService.findGeneralAssesmentById(generalAssesmentId)
                .orElseThrow(() -> new EntityNotFoundException("GeneralAssessment not found with id: " + generalAssesmentId));
    }

    @PutMapping("/updateTotalContributionForCourse/{courseId}")
    public ResponseEntity<String> updateTotalContributionForCourse(@PathVariable Long courseId, @RequestParam long generalAssesmentId, @RequestParam double newAssesmentContribution) {
        Optional<Course> course = courseRepository.findById(courseId);

        if (course.isPresent()) {
            generalAssesmentService.updateTotalContributionForCourse(course.get(), generalAssesmentId, newAssesmentContribution);
            return ResponseEntity.ok("Total contribution updated successfully for Course ID: " + courseId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course with ID: " + courseId + " not found");
        }
    }

    @GetMapping("/course/{courseId}/general-assessments")
    public ResponseEntity<List<GeneralAssessment>> getGeneralAssessmentsByCourseId(@PathVariable Long courseId) {
        try {
            List<GeneralAssessment> generalAssessments = generalAssesmentService.getGeneralAssessmentsByCourseId(courseId);
            return new ResponseEntity<>(generalAssessments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
