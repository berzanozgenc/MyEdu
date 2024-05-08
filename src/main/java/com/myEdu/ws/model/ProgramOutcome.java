package com.myEdu.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ProgramOutcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String department;//eklendi,

    @Column(nullable = true)
    private double target;

    @Column(nullable = true)
    private double assessmentValue;

    @Column(nullable = true)
    private double score;

    @Column(nullable = true)
    private double levelOfProvision;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "courseId")
    private Course course;

}
