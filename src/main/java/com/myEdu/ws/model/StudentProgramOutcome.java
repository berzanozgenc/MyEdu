package com.myEdu.ws.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentProgramOutcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne( cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User student;

    @ManyToOne( cascade = CascadeType.REMOVE)
    @JoinColumn(name = "program_outcome_id")
    private ProgramOutcome programOutcome;

    @Column(nullable = true)
    private double levelOfProvision;

}
