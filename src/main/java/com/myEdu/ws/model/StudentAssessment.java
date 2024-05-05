package com.myEdu.ws.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student_assessment")
public class StudentAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL) // Cascade type is set here
    @JoinColumn(name = "user_id")
    private User student;

    @ManyToOne(cascade = CascadeType.ALL) // Cascade type is set here
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    @Column(name = "grade")
    private double grade;

}