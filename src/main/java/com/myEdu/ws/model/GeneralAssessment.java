package com.myEdu.ws.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeneralAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long generalAssesmentId;

    @Getter
    @Column
    private String name;

    @Column
    private Double totalContribution;

    @Column
    private boolean questionBased = false;

    @ManyToOne
    @JoinColumn(name= "courseId")
    @JsonIgnore
    private Course course;

    @OneToMany(mappedBy = "generalAssessment")
    private List<Assessment> assessments;

}
