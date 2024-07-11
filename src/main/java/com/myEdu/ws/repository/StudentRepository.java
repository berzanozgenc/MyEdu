package com.myEdu.ws.repository;

import com.myEdu.ws.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findStudentByStudentNumber(int studentNumber);

    List<Student> findStudentsByDepartmentId(Long departmentId);
}