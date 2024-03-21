package com.myEdu.ws.repository;

import com.myEdu.ws.model.Student;
import com.myEdu.ws.model.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
    @Query("SELECT sc.student FROM StudentCourse sc WHERE sc.course.id = :courseId")
    List<Student> findStudentsByCourseId(Long courseId);
}