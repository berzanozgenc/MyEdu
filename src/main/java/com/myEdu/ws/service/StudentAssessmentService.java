package com.myEdu.ws.service;

import com.myEdu.ws.model.Assessment;
import com.myEdu.ws.model.StudentAssessment;
import com.myEdu.ws.repository.AssessmentRepository;
import com.myEdu.ws.repository.StudentAssessmentRepository;
import com.myEdu.ws.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class StudentAssessmentService {

    @Autowired
    private StudentAssessmentRepository studentAssessmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssessmentRepository assessmentRepository;

    public ResponseEntity<String> createStudentAssessment(Long userId, Long assessmentId, double grade) {
        try {
            StudentAssessment studentAssessment = new StudentAssessment();
            studentAssessment.setStudent(userRepository.findById(userId).orElse(null));
            studentAssessment.setAssessment(assessmentRepository.findById(assessmentId).orElse(null));
            studentAssessment.setGrade(grade);
            studentAssessmentRepository.save(studentAssessment);
            updateAverageGradesForAssessments(); // Ortalama notları güncelle
            return new ResponseEntity<>("Student assessment created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating student assessment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteStudentAssessment(Long userId, Long assessmentId) {
        try {
            studentAssessmentRepository.deleteByStudentUserIdAndAssessmentAssessmentId(userId, assessmentId);
            updateAverageGradesForAssessments(); // Ortalama notları güncelle
            return new ResponseEntity<>("Student assessment deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting student assessment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> updateStudentGrade(Long userId, Long assessmentId, double newGrade) {
        try {
            StudentAssessment studentAssessment = studentAssessmentRepository.findByStudentUserIdAndAssessmentAssessmentId(userId, assessmentId);
            if (studentAssessment != null) {
                studentAssessment.setGrade(newGrade);
                studentAssessmentRepository.save(studentAssessment);
                updateAverageGradesForAssessments(); // Ortalama notları güncelle
                return new ResponseEntity<>("Student grade updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Student assessment not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating student grade: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Double> getStudentGrade(Long userId, Long assessmentId) {
        try {
            StudentAssessment studentAssessment = studentAssessmentRepository.findByStudentUserIdAndAssessmentAssessmentId(userId, assessmentId);
            if (studentAssessment != null) {
                return new ResponseEntity<>(studentAssessment.getGrade(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void updateAverageGradesForAssessments() {
        List<Assessment> assessments = assessmentRepository.findAll();
        for (Assessment assessment : assessments) {
            double totalGrade = 0;
            int count = 0;
            List<StudentAssessment> studentAssessments = studentAssessmentRepository.findByAssessment_AssessmentId(assessment.getAssessmentId());
            for (StudentAssessment studentAssessment : studentAssessments) {
                totalGrade += studentAssessment.getGrade();
                count++;
            }
            double averageGrade = count > 0 ? totalGrade / count : 0;
            assessment.setAverageGrade(averageGrade);
            assessmentRepository.save(assessment);
        }
    }
}
