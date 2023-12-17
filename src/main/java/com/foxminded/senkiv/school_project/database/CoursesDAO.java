package com.foxminded.senkiv.school_project.database;

import com.foxminded.senkiv.school_project.database.mapper.CourseRowMapper;
import com.foxminded.senkiv.school_project.database.mapper.StudentRowMapper;
import com.foxminded.senkiv.school_project.exceptions.checked.CoursesTableUpdateException;
import com.foxminded.senkiv.school_project.exceptions.runtime.SchoolProjectRuntimeException;
import com.foxminded.senkiv.school_project.model.Course;
import com.foxminded.senkiv.school_project.model.Student;
import com.foxminded.senkiv.school_project.utils.Logging;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.*;


@Repository
public class CoursesDAO implements DAO<Course> {
	private final JdbcTemplate jdbcTemplate;
	private final CourseRowMapper courseRowMapper;
	private final StudentRowMapper studentRowMapper;
	private static final String SELECT_BY_ID_STATEMENT = "SELECT * FROM courses WHERE course_id = ? LIMIT 1;";
	private static final String SELECT_ALL_STATEMENT ="SELECT * FROM courses;";
	private static final String INSERT_STATEMENT = "INSERT INTO courses (course_name, course_description) VALUES (?, ?);";
	private static final String UPDATE_STATEMENT = "UPDATE courses SET course_name = ?, course_description = ? WHERE course_id = ?;";
	private static final String DELETE_STATEMENT ="DELETE FROM courses WHERE course_id = ?;";
	private static final String FILTER_STUDENTS_BY_COURSE = """
		SELECT students.student_id, students.group_id, students.first_name, students.last_name
		FROM students_courses
		JOIN students ON students_courses.student_id = students.student_id
		WHERE students_courses.course_id = ?
		ORDER BY students.student_id;""";
	private static final String GET_COURSES = """
		SELECT courses.course_id, courses.course_name, courses.course_description
		FROM students_courses
		JOIN courses ON students_courses.course_id = courses.course_id
		WHERE students_courses.student_id = ?""";

	public CoursesDAO(JdbcTemplate jdbcTemplate, CourseRowMapper courseRowMapper, StudentRowMapper studentRowMapper){
        this.jdbcTemplate = jdbcTemplate;
        this.courseRowMapper = courseRowMapper;
		this.studentRowMapper = studentRowMapper;
    }


	@Override
    public Optional<Course> get(int id)  {
        try{
			var list = jdbcTemplate.query(SELECT_BY_ID_STATEMENT, courseRowMapper, id);
			return Optional.ofNullable(list.get(0));
        } catch (EmptyResultDataAccessException e) {
			Logging.logger.info("No student with such id.");
			return Optional.empty();
        }
    }

    @Override
    public List<Course> getAll() {
        try{
			return jdbcTemplate.query(SELECT_ALL_STATEMENT, courseRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void create(Course course) {
		try {
			jdbcTemplate.update(INSERT_STATEMENT, course.getName(), course.getDescription());
		} catch (EmptyResultDataAccessException e) {
			throw new SchoolProjectRuntimeException(e);
		}
	}


    @Override
    public void update(Course course) {
		if(get(course.getId()).isEmpty()) {
			throw new CoursesTableUpdateException("No student with such id");
		}
		try {
			jdbcTemplate.update(UPDATE_STATEMENT, course.getName(), course.getDescription(), course.getId());
		} catch (DataAccessException e) {
			throw new CoursesTableUpdateException(course, e);
		}
    }

    @Override
    public void delete(int id) {
        try {
			jdbcTemplate.update(DELETE_STATEMENT, id);
        } catch (EmptyResultDataAccessException e) {
            throw new CoursesTableUpdateException(id, e);
        }
    }

	public List<Student> getStudents(int courseId){
		try {
			return jdbcTemplate.query(FILTER_STUDENTS_BY_COURSE, studentRowMapper, courseId);
		}catch(EmptyResultDataAccessException e){
			return new ArrayList<>();
		}
	}

	public List<Course> getCoursesForStudent(int studentId) {
		try {
			return jdbcTemplate.query(GET_COURSES, courseRowMapper, studentId);
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional
	public void batchCreate(List<Course> list){
		jdbcTemplate.batchUpdate(
			INSERT_STATEMENT,
			list,
			50,
			(PreparedStatement ps, Course course)->{
				ps.setString(1, course.getName());
				ps.setString(2, course.getDescription());
			}
			);
	}
}
