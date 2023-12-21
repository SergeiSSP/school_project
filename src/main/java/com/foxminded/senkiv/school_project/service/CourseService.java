package com.foxminded.senkiv.school_project.service;

import com.foxminded.senkiv.school_project.model.Course;
import com.foxminded.senkiv.school_project.model.Student;

import java.util.List;
import java.util.Optional;

public interface CourseService {
	Optional<Course> get(int id);
	List<Course> getAll();
	void create(Course course);
	void batchCreate(List<Course> list);
	void update(Course course);
	void delete(int id);
	List<Student> getStudents(int courseId);
	List<Course> getStudentCourses(int studentId);
}
