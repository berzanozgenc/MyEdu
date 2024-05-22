package com.myEdu.ws.repository;

import com.myEdu.ws.model.Course;
import com.myEdu.ws.model.Student;
import com.myEdu.ws.model.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
    @Query("SELECT sc.student FROM StudentCourse sc WHERE sc.course.id = :courseId")
    List<Student> findStudentsByCourseId(Long courseId);

    void deleteAllByStudentAndCourse(Student student, Course course);

    @Query("SELECT sc.course FROM StudentCourse sc WHERE sc.student= :student")
    List<Course> findCoursesByStudent(Student student);


}