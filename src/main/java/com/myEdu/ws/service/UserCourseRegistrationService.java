package com.myEdu.ws.service;

import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.User;
import com.myEdu.ws.model.UserCourseRegistration;
import com.myEdu.ws.repository.CourseRepository;
import com.myEdu.ws.repository.UserCourseRegistrationRepository;
import com.myEdu.ws.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserCourseRegistrationService {

    private final UserCourseRegistrationRepository userCourseRegistrationRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public UserCourseRegistrationService(UserCourseRegistrationRepository userCourseRegistrationRepository,
                                         UserRepository userRepository,
                                         CourseRepository courseRepository) {
        this.userCourseRegistrationRepository = userCourseRegistrationRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public void createUserCourseRegistration(Long userId, Long courseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course with ID " + courseId + " not found"));

        UserCourseRegistration userCourseRegistration = new UserCourseRegistration();
        userCourseRegistration.setUser(user);
        userCourseRegistration.setCourse(course);

        userCourseRegistrationRepository.save(userCourseRegistration);
    }

    @Transactional
    public void deleteUserCourseRegistration(Long registrationId) {
        userCourseRegistrationRepository.deleteById(registrationId);
    }

    @Transactional
    public List<Course> getCoursesByUserId(Long userId) {
        List<UserCourseRegistration> registrations = userCourseRegistrationRepository.findByUserUserId(userId);
        List<Course> courses = registrations.stream()
                .map(UserCourseRegistration::getCourse)
                .collect(Collectors.toList());
        return courses;
    }




}

