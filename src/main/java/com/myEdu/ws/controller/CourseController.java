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

    @GetMapping("/get-all-courses-name-and-code")
    public ResponseEntity<CourseNameAndCodeDto> getAllCourseNameAndCode() {
        CourseNameAndCodeDto dto = new CourseNameAndCodeDto();
        dto.setCoursesNameAndCode(courseService.getAllCourseNameAndCode());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{courseId}/nameAndCode")
    public String getCourseNameAndCodeById(@PathVariable Long courseId) {
        return courseService.getCourseNameAndCodeById(courseId);
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
