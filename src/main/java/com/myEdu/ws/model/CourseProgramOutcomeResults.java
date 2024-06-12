package com.myEdu.ws.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CourseProgramOutcomeResults {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Course course;

    @ManyToOne
    private ProgramOutcome programOutcome;

    @Column
    private double target;

    @Column
    private double assessmentValue;

    @Column
    private double score;

    @Column
    private double levelOfProvision;

}
