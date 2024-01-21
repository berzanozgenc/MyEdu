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
    public ResponseEntity<GeneralAssessment> addGeneralAssesment(@RequestBody GeneralAssessmentRequest request) {
        Long courseId = request.getCourseId();
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        GeneralAssessment generalAssessment = new GeneralAssessment();
        generalAssessment.setCourse(course);
        generalAssessment.setName(request.getName());
        generalAssessment.setTotalContribution(request.getTotalContribution());

        if (generalAssesmentService.isTotalContributionUnderLimit(course, generalAssessment.getTotalContribution())) {
            GeneralAssessment savedGeneralAssessment = generalAssesmentService.addGeneralAssesment(generalAssessment);
            return new ResponseEntity<>(savedGeneralAssessment, HttpStatus.CREATED);
        } else {
            throw new IllegalArgumentException("Toplam değerlendirme katkısı limiti aşıldı!");
        }
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

}
