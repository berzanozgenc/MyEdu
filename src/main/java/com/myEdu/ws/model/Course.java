package com.myEdu.ws.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @Column(nullable = false)
    private String courseName;

    @Column(nullable = false)
    private int ects;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private int credit;

    @Column(nullable = false)
    private String semester;

    @Column(nullable = false)
    private int section;

    @OneToMany(mappedBy = "course")
    private List<GeneralAssesment> generalAssesment;
}
