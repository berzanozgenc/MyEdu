package com.myEdu.ws.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "learning_outcome_program_outcome")
public class LearningOutcomeProgramOutcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double contribution;

    @ManyToOne
    @JoinColumn(name = "learning_outcome_id", referencedColumnName = "id")
    private LearningOutcome learningOutcome;

    @ManyToOne
    @JoinColumn(name = "program_outcome_id", referencedColumnName = "id")
    private ProgramOutcome programOutcome;

}

