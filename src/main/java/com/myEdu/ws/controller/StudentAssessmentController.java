package com.myEdu.ws.controller;

import com.myEdu.ws.model.StudentAssessment;
import com.myEdu.ws.service.StudentAssessmentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
@RequestMapping("/student-assessment")
public class StudentAssessmentController {

    @Autowired
    private StudentAssessmentService studentAssessmentService;

    @PostMapping("/create")
    public ResponseEntity<String> createStudentAssessment(@RequestParam Long userId,
                                                          @RequestParam Long assessmentId,
                                                          @RequestParam double grade) {
        return studentAssessmentService.createStudentAssessment(userId, assessmentId, grade);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStudentAssessment(@RequestParam Long userId,
                                                          @RequestParam Long assessmentId) {
        return studentAssessmentService.deleteStudentAssessment(userId, assessmentId);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateStudentGrade(@RequestParam Long userId,
                                                     @RequestParam Long assessmentId,
                                                     @RequestParam double newGrade) {
        return studentAssessmentService.updateStudentGrade(userId, assessmentId, newGrade);
    }

    @GetMapping("/get-grade")
    public ResponseEntity<Double> getStudentGrade(@RequestParam Long userId,
                                                  @RequestParam Long assessmentId) {
        return studentAssessmentService.getStudentGrade(userId, assessmentId);
    }
}
