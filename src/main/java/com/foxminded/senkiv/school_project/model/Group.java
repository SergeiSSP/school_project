package com.foxminded.senkiv.school_project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="groups")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class Group {
    @Id
    private int id;
    @Column(name="group_name",nullable = false)
	@NonNull
    private String name;
}
