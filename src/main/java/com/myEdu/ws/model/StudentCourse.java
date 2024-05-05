package com.myEdu.ws.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "STUDENT_COURSE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL) // Cascade type is set here
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL) // Cascade type is set here
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    public StudentCourse(Student student, Course course) {
        this.student = student;
        this.course = course;
    }
}
