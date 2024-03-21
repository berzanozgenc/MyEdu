package com.myEdu.ws.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="MYEDU_STUDENT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student extends User {

    @Column(nullable = true)
    private int studentNumber;

}
