package com.myEdu.ws.controller;

import com.myEdu.ws.dto.UserCourseRegistrationRequest;
import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.UserCourseRegistration;
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
        // Check if another user is already registered for the course
        if (userCourseRegistrationService.isAnotherUserAlreadyRegisteredForCourse(request.getUserId(), request.getCourseId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Another user is already registered for the course");
        }

        // If another user is not registered, proceed with creating the registration
        userCourseRegistrationService.createUserCourseRegistration(request.getUserId(), request.getCourseId());
        return ResponseEntity.status(HttpStatus.CREATED).body("User course registration created successfully");
    }

    @DeleteMapping("/{registrationId}")
    public ResponseEntity<String> deleteUserCourseRegistration(@PathVariable Long registrationId) {
        userCourseRegistrationService.deleteUserCourseRegistration(registrationId);
        return ResponseEntity.ok("User course registration deleted successfully");
    }

    @DeleteMapping("delete/instructor/{courseId}")
    public ResponseEntity<String> deleteUserCourseRegistrationByCourseId(@PathVariable Long courseId) {
        userCourseRegistrationService.deleteUserCourseRegistrationByCourseId(courseId);
        return ResponseEntity.ok("User course registration deleted successfully");
    }

    @GetMapping("/user/{userId}/courses")
    public ResponseEntity<List<UserCourseRegistration>> getUserCourseRegistrationsByUserId(@PathVariable Long userId) {
        List<UserCourseRegistration> registrations = userCourseRegistrationService.getUserCourseRegistrationsByUserId(userId);
        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/course/{courseId}/user")
    public ResponseEntity<List<UserCourseRegistration>> getUserCourseRegistrationsByCourse(@PathVariable Long courseId) {
        List<UserCourseRegistration> registrations = userCourseRegistrationService.getUserCourseRegistrationsByCourseId(courseId);
        return ResponseEntity.ok(registrations);
    }

}
