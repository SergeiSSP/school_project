package com.foxminded.senkiv.school_project.database;

import com.foxminded.senkiv.school_project.database.mapper.StudentRowMapper;
import com.foxminded.senkiv.school_project.exceptions.checked.StudentsTableUpdateException;
import com.foxminded.senkiv.school_project.exceptions.checked.StudentsCoursesTableUpdateException;
import com.foxminded.senkiv.school_project.exceptions.runtime.SchoolProjectRuntimeException;
import com.foxminded.senkiv.school_project.model.Student;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class StudentsDAO implements DAO<Student> {
	private final JdbcTemplate jdbcTemplate;
	private final StudentRowMapper rowMapper;
	private static final String SELECT_BY_ID_STATEMENT = "SELECT * FROM students WHERE student_id = ? LIMIT 1;";
	private static final String SELECT_ALL_STATEMENT = "SELECT * FROM students;";
	private static final String INSERT_STATEMENT = "INSERT INTO students (first_name, last_name) VALUES (?, ?);";
	private static final String UPDATE_STATEMENT = "UPDATE students SET  first_name = ?, last_name = ? WHERE student_id = ?;";
	private static final String DELETE_STATEMENT = "DELETE FROM students WHERE student_id = ?;";
	private static final String ASSIGN_STUDENT_GROUP = "UPDATE students SET group_id = ? WHERE student_id = ?;";
	private static final String ASSIGN_STUDENT_COURSE = "INSERT INTO students_courses (student_id, course_id) VALUES (?, ?);";
	private static final String DELETE_STUDENT_FROM_COURSE = "DELETE FROM students_courses WHERE student_id = ? AND course_id = ?;";

	public StudentsDAO(JdbcTemplate jdbcTemplate, StudentRowMapper rowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.rowMapper = rowMapper;
	}

	@Override
	public Optional<Student> get(int id) {
		try {
			var list = jdbcTemplate.query(SELECT_BY_ID_STATEMENT, rowMapper, id);
			return Optional.ofNullable(list.get(0));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Student> getAll() {
		try {
			return jdbcTemplate.query(SELECT_ALL_STATEMENT, rowMapper);
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<>();
		}
	}

	@Override
	public void create(Student student) {
		try {
			jdbcTemplate.update(INSERT_STATEMENT, student.getFirstName(), student.getLastName());
		} catch (EmptyResultDataAccessException e) {
			throw new StudentsTableUpdateException(student, e);
		}
	}

	@Override
	public void update(Student student) {
		try {
			jdbcTemplate.update(UPDATE_STATEMENT, student.getFirstName(), student.getLastName(), student.getId());
		} catch (EmptyResultDataAccessException e) {
			throw new StudentsTableUpdateException(student, e);
		}
	}

	@Override
	public void delete(int id) {
		try {
			jdbcTemplate.update(DELETE_STATEMENT, id);
		} catch (EmptyResultDataAccessException e) {
			throw new StudentsTableUpdateException(id, e);
		}
	}

	public void addGroup(int groupId, int studentId) {
		try {
			jdbcTemplate.update(ASSIGN_STUDENT_GROUP, groupId, studentId);
		} catch (EmptyResultDataAccessException e) {
			throw new StudentsTableUpdateException(studentId, e);
		}
	}


	public void addCourse(int studentId, int courseId) {
		try {
			jdbcTemplate.update(ASSIGN_STUDENT_COURSE, studentId, courseId);
		} catch (EmptyResultDataAccessException e) {
			throw new SchoolProjectRuntimeException(e);
		}
	}

	public void deleteCourse(int studentId, int courseId) {
		try {
			jdbcTemplate.update(DELETE_STUDENT_FROM_COURSE, studentId, courseId);
		} catch (EmptyResultDataAccessException e) {
			throw new StudentsCoursesTableUpdateException(studentId, courseId, e);
		}
	}

	@Override
	@Transactional
	public void batchCreate(List<Student> list){
		jdbcTemplate.batchUpdate(
			INSERT_STATEMENT,
			list,
			50,
			(PreparedStatement ps, Student student)-> {
				ps.setString(1, student.getFirstName());
				ps.setString(2, student.getLastName());
			});
	}
}

