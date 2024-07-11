package com.myEdu.ws.service;

import com.myEdu.ws.model.Student;
import com.myEdu.ws.model.User;
import com.myEdu.ws.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student getStudentIdByStudentNumber(int number){
        return studentRepository.findStudentByStudentNumber(number);
    }

    public Student createStudent(Student studentDto) {
        String encodedPassword = passwordEncoder.encode(studentDto.getPassword());
        Student student = new Student();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setEmail(studentDto.getEmail());
        student.setStatusCode(studentDto.getStatusCode());
        student.setPassword(encodedPassword);
        student.setStudentNumber(studentDto.getStudentNumber());
        student.setDepartment(studentDto.getDepartment());
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> getStudentsByDepartmentId(Long id) {
        return studentRepository.findStudentsByDepartmentId(id);
    }
}
