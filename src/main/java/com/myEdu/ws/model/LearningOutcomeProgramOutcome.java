package com.myEdu.ws.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "learning_outcome_program_outcome")
@AllArgsConstructor
@NoArgsConstructor
public class LearningOutcomeProgramOutcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double contribution;

    @ManyToOne(cascade = CascadeType.ALL) // Cascade type is set here
    @JoinColumn(name = "learning_outcome_id", referencedColumnName = "id")
    private LearningOutcome learningOutcome;

    @ManyToOne(cascade = CascadeType.ALL) // Cascade type is set here
    @JoinColumn(name = "program_outcome_id", referencedColumnName = "id")
    private ProgramOutcome programOutcome;

}

