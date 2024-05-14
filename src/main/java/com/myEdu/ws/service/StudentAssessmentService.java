package com.myEdu.ws.service;

import com.myEdu.ws.model.*;
import com.myEdu.ws.repository.AssessmentRepository;
import com.myEdu.ws.repository.StudentAssessmentRepository;
import com.myEdu.ws.repository.StudentRepository;
import com.myEdu.ws.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class StudentAssessmentService {

    @Autowired
    private StudentAssessmentRepository studentAssessmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    public StudentAssessment createStudentAssessment(Long userId, Long assessmentId, double grade) {
        Assessment assessment = assessmentRepository.findById(assessmentId).orElse(null);
        Student student = studentRepository.findById(userId).orElse(null);
        if (student != null && assessment != null) {
            StudentAssessment relationship = new StudentAssessment();
            relationship.setAssessment(assessment);
            relationship.setStudent(student);
            relationship.setGrade(grade);
            studentAssessmentRepository.save(relationship);
            updateAverageGradesForAssessments();
            return studentAssessmentRepository.save(relationship);
        }
        return null;
    }

    public StudentAssessment updateGrade(StudentAssessment studentAssessment) {
        return studentAssessmentRepository.save(studentAssessment);
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
            if(count != 0){
                double averageGrade = totalGrade / count;
                assessment.setAverageGrade(averageGrade);
                assessmentRepository.save(assessment);
            }
        }
    }

    public StudentAssessment getStudentAssessment(Long assessmentId, Long userId) {
        Optional<StudentAssessment> relationship = studentAssessmentRepository.findByAssessmentAssessmentIdAndStudentUserId(assessmentId, userId);
        return relationship.orElse(null);
    }

}
