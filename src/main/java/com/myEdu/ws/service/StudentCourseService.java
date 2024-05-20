package com.myEdu.ws.service;

import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.Student;
import com.myEdu.ws.model.StudentCourse;
import com.myEdu.ws.repository.CourseRepository;
import com.myEdu.ws.repository.StudentCourseRepository;
import com.myEdu.ws.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCourseService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentCourseRepository studentCourseRepository;

    @Autowired
    public StudentCourseService(StudentRepository studentRepository, CourseRepository courseRepository,
                                StudentCourseRepository studentCourseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.studentCourseRepository = studentCourseRepository;
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
        System.out.println(s.getStudentNumber());
        System.out.println("*****************");
        System.out.println(c.getCourseName());
        studentCourseRepository.deleteAllByStudentAndCourse(s, c);
    }

}
