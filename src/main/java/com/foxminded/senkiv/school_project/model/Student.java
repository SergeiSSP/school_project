package com.foxminded.senkiv.school_project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Table(name="students")
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Student {
    @Id
    private int id;
    @Column(name ="group_id", nullable = false)
	@EqualsAndHashCode.Exclude
    private int groupId;
    @Column(name="first_name", nullable = false)
	@NonNull
    private String firstName;
    @Column(name="last_name", nullable = false)
	@NonNull
    private String lastName;

}
