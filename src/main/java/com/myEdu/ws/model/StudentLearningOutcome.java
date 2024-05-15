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
public class StudentLearningOutcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne( cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User student;

    @ManyToOne( cascade = CascadeType.REMOVE)
    @JoinColumn(name = "learning_outcome_id")
    private LearningOutcome learningOutcome;

    @Column(nullable = true)
    private double levelOfProvision;

}
