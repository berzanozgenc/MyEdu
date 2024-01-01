package com.myEdu.ws.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeneralAssesment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long generalAssesmentId;

    @Column
    private String name;

    @Column
    private Double totalContribution;

    @ManyToOne
    @JoinColumn(name= "courseId")
    private Course course;
}
