package com.myEdu.ws.controller;

import com.myEdu.ws.service.StudentCourseService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.myEdu.ws.model.Student;
import java.util.List;


@RestController
@RequestMapping("/student-course")
public class StudentCourseController {

    private final StudentCourseService studentCourseService;

    @Autowired
    public StudentCourseController(StudentCourseService studentCourseService) {
        this.studentCourseService = studentCourseService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createStudentCourseRelationship(@RequestBody StudentCourseRequest request) {
        try {
            studentCourseService.createStudentCourseRelationship(request.getStudentId(), request.getCourseId());
            return new ResponseEntity<>("Student-course relationship created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/student-course/{relationshipId}")
    public ResponseEntity<String> deleteStudentCourseRelationship(@PathVariable Long relationshipId) {
        try {
            studentCourseService.deleteStudentCourseRelationshipById(relationshipId);
            return new ResponseEntity<>("Öğrenci-ders ilişkisi başarıyla silindi", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Öğrenci-ders ilişkisi silinirken bir hata oluştu", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<Student>> getStudentsByCourseId(@PathVariable Long courseId) {
        List<Student> students = studentCourseService.getStudentsByCourseId(courseId);
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
}

@Setter
@Getter
class StudentCourseRequest {
    private Long studentId;
    private Long courseId;

}
