package com.myEdu.ws.repository;

import com.myEdu.ws.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findStudentByStudentNumber(int studentNumber);

}