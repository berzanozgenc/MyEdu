package com.myEdu.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LearningOutcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(nullable = true)
    private double desiredTarget;

    @Column(nullable = true)
    private double assessmentSum;

    @Column(nullable = true)
    private double scoreSum;

    @Column(nullable = true)
    private double levelOfProvision;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "courseId")
    private Course course;

}
