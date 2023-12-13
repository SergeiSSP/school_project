package com.foxminded.senkiv.school_project.model;

import com.google.common.base.Objects;
import jakarta.persistence.*;

@Entity
@Table(name="courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="course_name", nullable = false)
    private String name;
    @Column(name="course_description", nullable = false)
    private String description;

    public Course() {}

    public Course(String name) {
        this.name = name;
    }

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Course(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && Objects.equal(name, course.name) && Objects.equal(description, course.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, description);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Course{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
