package com.myEdu.ws.service;

import com.myEdu.ws.model.*;
import com.myEdu.ws.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentCourseService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final GeneralAssessmentRepository generalAssessmentRepository;
    private final AssessmentRepository assessmentRepository;
    private final StudentAssessmentRepository studentAssessmentRepository;

    @Autowired
    public StudentCourseService(StudentRepository studentRepository, CourseRepository courseRepository,
                                StudentCourseRepository studentCourseRepository, GeneralAssessmentRepository generalAssessmentRepository, AssessmentRepository assessmentRepository, StudentAssessmentRepository studentAssessmentRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.studentCourseRepository = studentCourseRepository;
        this.generalAssessmentRepository = generalAssessmentRepository;
        this.assessmentRepository = assessmentRepository;
        this.studentAssessmentRepository = studentAssessmentRepository;
    }

    public void createStudentCourseRelationship(Long studentId, Long courseId) {
        // Verilen studentId ve courseId'ye göre ilgili öğrenci ve kursu al
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Öğrenci bulunamadı"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Kurs bulunamadı"));

        // Yeni bir StudentCourse nesnesi oluştur ve kaydet
        StudentCourse studentCourse = new StudentCourse(student, course);
        studentCourseRepository.save(studentCourse);
    }
    public void deleteStudentCourseRelationshipById(Long relationshipId) {
        studentCourseRepository.deleteById(relationshipId);
    }

    public List<Student> getStudentsByCourseId(Long courseId) {
        return studentCourseRepository.findStudentsByCourseId(courseId);
    }

    @Transactional
    public void deleteByStudentUserIdAndCourseCourseId(Long studentId, Long courseId) {
        Student s = studentRepository.findById(studentId).get();
        Course c = courseRepository.findById(courseId).get();
        List<GeneralAssessment> generalAssessments = generalAssessmentRepository.findByCourse_courseId(c.getCourseId());
        for (GeneralAssessment generalAssessment : generalAssessments){
            List<Assessment> assessments = assessmentRepository.findByGeneralAssessment(generalAssessment);
            for (Assessment assessment : assessments){
                studentAssessmentRepository.deleteStudentAssessment(s.getUserId(),assessment.getAssessmentId());
            }
        }
        studentCourseRepository.deleteAllByStudentAndCourse(s, c);
    }

    public List<Course> getCoursesByStudentId(Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);

        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            return studentCourseRepository.findCoursesByStudent(student);
        } else {
            // İlgili öğrenci bulunamadı, uygun bir hata mesajı döndürebilirsiniz.
            throw new EntityNotFoundException("Öğrenci bulunamadı: " + studentId);
        }
    }
}
