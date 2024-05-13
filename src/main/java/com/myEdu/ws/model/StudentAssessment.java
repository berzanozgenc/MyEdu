package com.myEdu.ws.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import static jakarta.persistence.ConstraintMode.CONSTRAINT;

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

    @ManyToOne( cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User student;

    @ManyToOne( cascade = CascadeType.REMOVE)
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    @Column(name = "grade")
    private double grade;

}