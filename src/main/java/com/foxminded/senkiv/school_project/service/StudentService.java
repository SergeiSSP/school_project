package com.foxminded.senkiv.school_project.service;

import com.foxminded.senkiv.school_project.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
	void create(Student student);
	void batchCreate(List<Student> list);
	Optional<Student> get(int id);
	List<Student> getAll();
	void update(Student student);

	void delete(int id);
	void addGroup(int groupId, int studentId);
	void addCourse(int studentId, int courseId);
	void deleteCourse(int studentId, int courseId);
}
