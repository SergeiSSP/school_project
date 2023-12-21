package com.foxminded.senkiv.school_project.service;

import com.foxminded.senkiv.school_project.database.StudentsDAO;
import com.foxminded.senkiv.school_project.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
	private final StudentsDAO studentsDao;

	@Autowired
	public StudentServiceImpl(StudentsDAO studentsDao) {
		this.studentsDao = studentsDao;
	}

	@Override
	public void create(Student student) {
		studentsDao.create(student);
	}

	@Override
	public void batchCreate(List<Student> list) {
		studentsDao.batchCreate(list);
	}

	@Override
	public Optional<Student> get(int id) {
		return studentsDao.get(id);
	}

	@Override
	public List<Student> getAll() {
		return studentsDao.getAll();
	}

	@Override
	public void update(Student student) {
		studentsDao.update(student);
	}

	@Override
	public void delete(int id) {
		studentsDao.delete(id);
	}

	@Override
	public void addGroup(int groupId, int studentId) {
		studentsDao.addGroup(groupId, studentId);
	}

	@Override
	public void addCourse(int studentId, int courseId) {
		studentsDao.addCourse(studentId, courseId);
	}

	@Override
	public void deleteCourse(int studentId, int courseId) {
		studentsDao.deleteCourse(studentId, courseId);
	}
}
