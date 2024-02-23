package com.myEdu.ws.controller;

import com.myEdu.ws.dto.UserCourseRegistrationRequest;
import com.myEdu.ws.model.Course;
import com.myEdu.ws.service.UserCourseRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-course-registrations")
public class UserCourseRegistrationController {

    private final UserCourseRegistrationService userCourseRegistrationService;

    @Autowired
    public UserCourseRegistrationController(UserCourseRegistrationService userCourseRegistrationService) {
        this.userCourseRegistrationService = userCourseRegistrationService;
    }

    @PostMapping
    public ResponseEntity<String> createUserCourseRegistration(@RequestBody UserCourseRegistrationRequest request) {
        userCourseRegistrationService.createUserCourseRegistration(request.getUserId(), request.getCourseId());
        return ResponseEntity.status(HttpStatus.CREATED).body("User course registration created successfully");
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<String> deleteUserCourseRegistration(@PathVariable Long registrationId) {
        userCourseRegistrationService.deleteUserCourseRegistration(registrationId);
        return ResponseEntity.ok("User course registration deleted successfully");
    }

    @GetMapping("/user/{userId}/courses")
    public ResponseEntity<List<Course>> getCoursesByUserId(@PathVariable Long userId) {
        List<Course> courses = userCourseRegistrationService.getCoursesByUserId(userId);
        return ResponseEntity.ok(courses);
    }

}
