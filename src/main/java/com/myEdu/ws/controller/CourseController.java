package com.myEdu.ws.controller;

import com.myEdu.ws.dto.CourseDto;
import com.myEdu.ws.dto.CourseNameAndCodeDto;
import com.myEdu.ws.model.Course;
import com.myEdu.ws.repository.CourseRepository;
import com.myEdu.ws.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@AllArgsConstructor
public class CourseController {

    private CourseService courseService;

    @GetMapping("/get-courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/get-courses/department/{departmentId}")
    public ResponseEntity<List<Course>> getDepartmentCourses(@PathVariable Long departmentId) {
        List<Course> courses = courseService.getDepartmentCourses(departmentId);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long courseId) {
        Course course = courseService.getCourseById(courseId);
        if (course != null) {
            return new ResponseEntity<>(course, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long courseId, @RequestBody Course updatedCourse) {
        try {
            Course updated = courseService.updateCourse(courseId, updatedCourse);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create-course")
    public ResponseEntity<String> createCourse(@RequestBody CourseDto courseDto) {
        Long idOfCreatedCourse = courseService.createCourse(courseDto);
        return new ResponseEntity<>(idOfCreatedCourse + " id' si ile ders oluşturuldu", HttpStatus.CREATED);
    }

    @DeleteMapping("/{courseId}")
    public void deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
    }
}
