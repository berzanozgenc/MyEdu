package com.myEdu.ws.repository;

import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.User;
import com.myEdu.ws.model.UserCourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCourseRegistrationRepository extends JpaRepository<UserCourseRegistration, Long> {
    List<UserCourseRegistration> findByUserUserId(Long userId);

    boolean existsByUserUserIdAndCourseCourseId(Long userId, Long courseId);

    List<UserCourseRegistration> findByCourseCourseId(Long courseId);

    void deleteByCourseCourseId(Long courseId);
}

