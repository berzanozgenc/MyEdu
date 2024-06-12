package com.myEdu.ws.controller;

import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.CourseProgramOutcomeResults;
import com.myEdu.ws.repository.CourseRepository;
import com.myEdu.ws.service.CourseProgramOutcomeResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courseProgramOutcomeResults")
public class CourseProgramOutcomeResultsController {

    @Autowired
    private CourseProgramOutcomeResultsService courseProgramOutcomeResultsService;

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/{courseId}")
    public ResponseEntity<String> createCourseProgramOutcomeResults(
            @PathVariable Long courseId) {
        try {
            Optional<Course> course = courseRepository.findById(courseId);
            return courseProgramOutcomeResultsService.createCourseProgramOutcomeResults(course.get());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<List<CourseProgramOutcomeResults>> getCourseProgramOutcomeResultsByCourse(
            @PathVariable Long courseId) {
        try {
            List<CourseProgramOutcomeResults> results = courseProgramOutcomeResultsService.getCourseProgramOutcomeResultsByCourse(courseId);
            if (results.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}