package com.myEdu.ws.controller;

import com.myEdu.ws.dto.AssessmentRequest;
import com.myEdu.ws.model.Assessment;
import com.myEdu.ws.model.GeneralAssessment;
import com.myEdu.ws.service.AssessmentService;
import com.myEdu.ws.service.GeneralAssessmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;
    private final GeneralAssessmentService generalAssessmentService;

    @Autowired
    public AssessmentController(AssessmentService assessmentService, GeneralAssessmentService generalAssessmentService) {
        this.assessmentService = assessmentService;
        this.generalAssessmentService = generalAssessmentService;
    }

    @PostMapping("/create-assessment")
    public ResponseEntity<Assessment> createAssessment(@RequestBody AssessmentRequest request) {
        Long generalAssessmentId = request.getGeneralAssessmentId();
        GeneralAssessment generalAssessment = generalAssessmentService.findById(generalAssessmentId)
                .orElseThrow(() -> new EntityNotFoundException("GeneralAssessment not found with id: " + generalAssessmentId));

        // Yeni eklenmek istenen contribution
        Double newContribution = request.getContribution();

        // GeneralAssessment'a ait olan mevcut Assessment'ların contribution toplamını al
        Double totalContribution = assessmentService.getTotalContributionByGeneralAssessment(generalAssessment);

        // Yeni eklenmek istenen contribution ile toplamın ne kadar olacağını kontrol et
        if (totalContribution + newContribution <= 100.0) {
            Assessment assessment = new Assessment();
            assessment.setName(request.getName());
            assessment.setContribution(newContribution);
            assessment.setAverageGrade(request.getAverageGrade());
            assessment.setGeneralAssessment(generalAssessment);

            Assessment savedAssessment = assessmentService.createAssessment(assessment);
            return new ResponseEntity<>(savedAssessment, HttpStatus.CREATED);
        } else {

            throw new IllegalArgumentException("Total contribution exceeds 100% for GeneralAssessment with id: " + generalAssessmentId);
        }
    }


    @GetMapping("/get-assessment/{id}")
    public Assessment getAssessmentById(@PathVariable("id") long assessmentId) {
        return assessmentService.findAssessmentById(assessmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assessment not found with id: " + assessmentId));
    }

    @GetMapping("/get-all-assessments")
    public List<Assessment> getAllAssessments() {
        return assessmentService.getAllAssessments();
    }

    @PutMapping("/update-assessment/{id}")
    public ResponseEntity<String> updateAssessment(@PathVariable long id, @RequestBody AssessmentRequest request) {
        assessmentService.updateAssessment(id, request);
        return ResponseEntity.ok("Assessment updated successfully");
    }

    @DeleteMapping("/delete-assessment/{id}")
    public void deleteAssessment(@PathVariable("id") long assessmentId) {
        assessmentService.deleteAssessmentById(assessmentId);
    }

    @GetMapping("/generalAssessment/{generalAssessmentId}")
    public List<Assessment> getAssessmentsByGeneralAssessmentId(@PathVariable Long generalAssessmentId) {
        return assessmentService.getAssessmentsByGeneralAssessmentId(generalAssessmentId);
    }

    @PutMapping("/update-assessment-contribution/{id}")
    public ResponseEntity<String> updateAssessmentContribution(@PathVariable long id, @RequestParam Double newContribution) {
        assessmentService.updateAssessmentContribution(id, newContribution);
        return ResponseEntity.ok("Assessment contribution updated successfully");
    }
}
