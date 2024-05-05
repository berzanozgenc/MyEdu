package com.myEdu.ws.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"userId", "courseId"})
})
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCourseRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registrationId;

    @ManyToOne(cascade = CascadeType.ALL) // Cascade type is set here
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL) // Cascade type is set here
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

}
