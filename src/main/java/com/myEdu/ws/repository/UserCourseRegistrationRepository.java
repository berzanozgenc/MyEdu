package com.myEdu.ws.repository;

import com.myEdu.ws.model.UserCourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCourseRegistrationRepository extends JpaRepository<UserCourseRegistration, Long> {
    List<UserCourseRegistration> findByUserUserId(Long userId);
}

