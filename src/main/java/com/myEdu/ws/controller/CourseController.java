package com.myEdu.ws.controller;

import com.myEdu.ws.dto.CourseDto;
import com.myEdu.ws.dto.CourseNameAndCodeDto;
import com.myEdu.ws.model.Course;
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

    @PostMapping("/create-course")
    public ResponseEntity<String> createCourse(@RequestBody CourseDto courseDto) {
        Long idOfCreatedCourse = courseService.createCourse(courseDto);
        return new ResponseEntity<>(idOfCreatedCourse + " id' si ile ders oluşturuldu", HttpStatus.CREATED);
    }
}
