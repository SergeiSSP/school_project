package com.foxminded.senkiv.school_project.service;

import com.foxminded.senkiv.school_project.database.CoursesDAO;
import com.foxminded.senkiv.school_project.model.Course;
import com.foxminded.senkiv.school_project.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
	private final CoursesDAO coursesDao;

	@Autowired
	public CourseServiceImpl(CoursesDAO coursesDao) {
        this.coursesDao = coursesDao;
    }

	@Override
	public List<Course> getAll() {
		return coursesDao.getAll();
	}

	@Override
	public Optional<Course> get(int id) {
		return coursesDao.get(id);
	}

	@Override
	public void create(Course course) {
		coursesDao.create(course);
	}

	@Override
	public void batchCreate(List<Course> list) {
		coursesDao.batchCreate(list);
	}

	@Override
	public void update(Course course) {
		coursesDao.update(course);
	}

	@Override
	public void delete(int id) {
		coursesDao.delete(id);
	}

	@Override
	public List<Course> getStudentCourses(int studentId) {
		return coursesDao.getCoursesForStudent(studentId);
	}

	@Override
	public List<Student> getStudents(int courseId) {
		return coursesDao.getStudents(courseId);
	}
}
