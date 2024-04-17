package com.myEdu.ws.controller;

import com.myEdu.ws.model.StudentAssessment;
import com.myEdu.ws.service.StudentAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/studentAssessments")
public class StudentAssessmentController {

    private final StudentAssessmentService studentAssessmentService;

    @Autowired
    public StudentAssessmentController(StudentAssessmentService studentAssessmentService) {
        this.studentAssessmentService = studentAssessmentService;
    }

    // Öğrenci notu oluşturma isteği
    @PostMapping
    public ResponseEntity<StudentAssessment> createStudentAssessment(@RequestBody StudentAssessment studentAssessment) {
        StudentAssessment createdStudentAssessment = studentAssessmentService.saveStudentAssessment(studentAssessment);
        return new ResponseEntity<>(createdStudentAssessment, HttpStatus.CREATED);
    }

    // Öğrenci notunu güncelleme isteği
    @PutMapping("/{id}")
    public ResponseEntity<StudentAssessment> updateStudentAssessment(@PathVariable Long id, @RequestBody StudentAssessment studentAssessment) {
        Optional<StudentAssessment> existingStudentAssessment = studentAssessmentService.findStudentAssessmentById(id);
        if (existingStudentAssessment.isPresent()) {
            studentAssessment.setId(id);
            StudentAssessment updatedStudentAssessment = studentAssessmentService.updateStudentAssessment(studentAssessment);
            return new ResponseEntity<>(updatedStudentAssessment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/student/{studentId}/assessment/{assessmentId}")
    public ResponseEntity<Double> getGradeByStudentAndAssessment(@PathVariable Long studentId, @PathVariable Long assessmentId) {
        Optional<StudentAssessment> studentAssessment = studentAssessmentService.findStudentAssessmentByStudentIdAndAssessmentId(studentId, assessmentId);
        if (studentAssessment.isPresent()) {
            Double grade = studentAssessment.get().getGrade();
            return new ResponseEntity<>(grade, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Öğrenci notunu silme isteği
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentAssessment(@PathVariable Long id) {
        Optional<StudentAssessment> existingStudentAssessment = studentAssessmentService.findStudentAssessmentById(id);
        if (existingStudentAssessment.isPresent()) {
            studentAssessmentService.deleteStudentAssessmentById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
