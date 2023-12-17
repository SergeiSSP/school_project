package com.foxminded.senkiv.school_project.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="courses")
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="course_name", nullable = false)
	@NonNull
    private String name;
    @Column(name="course_description", nullable = false)
	@NonNull
	@EqualsAndHashCode.Exclude
    private String description;
}
