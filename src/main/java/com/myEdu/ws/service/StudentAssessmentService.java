package com.myEdu.ws.service;

import com.myEdu.ws.model.StudentAssessment;
import com.myEdu.ws.repository.StudentAssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentAssessmentService {

    private final StudentAssessmentRepository studentAssessmentRepository;

    @Autowired
    public StudentAssessmentService(StudentAssessmentRepository studentAssessmentRepository) {
        this.studentAssessmentRepository = studentAssessmentRepository;
    }

    public Optional<StudentAssessment> findStudentAssessmentByStudentIdAndAssessmentId(Long studentId, Long assessmentId) {
        return studentAssessmentRepository.findByStudentUserIdAndAssessmentAssessmentId(studentId, assessmentId);
    }

    // Öğrenci notunu kaydetmek için
    public StudentAssessment saveStudentAssessment(StudentAssessment studentAssessment) {
        return studentAssessmentRepository.save(studentAssessment);
    }

    // Öğrenci notunu güncellemek için
    public StudentAssessment updateStudentAssessment(StudentAssessment updatedStudentAssessment) {
        return studentAssessmentRepository.save(updatedStudentAssessment);
    }

    // Öğrenci notunu ID'ye göre silmek için
    public void deleteStudentAssessmentById(Long id) {
        studentAssessmentRepository.deleteById(id);
    }

    // Öğrenci notunu ID'ye göre bulmak için
    public Optional<StudentAssessment> findStudentAssessmentById(Long id) {
        return studentAssessmentRepository.findById(id);
    }
}
