package com.foxminded.senkiv.school_project.database;

import com.foxminded.senkiv.school_project.database.mapper.CourseRowMapper;
import com.foxminded.senkiv.school_project.database.mapper.GroupRowMapper;
import com.foxminded.senkiv.school_project.database.mapper.StudentRowMapper;
import com.foxminded.senkiv.school_project.model.Course;
import com.foxminded.senkiv.school_project.model.Student;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"classpath:initial_table_schema.sql", "classpath:insert_test_data.sql"},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CoursesDAOTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	StudentRowMapper studentRowMapper;
	@Autowired
	GroupRowMapper groupRowMapper;
	@Autowired
	CourseRowMapper courseRowMapper;
	private  StudentsDAO studentsDAO;
	private GroupsDAO groupsDAO;
	private CoursesDAO coursesDAO;

	@BeforeEach
	void setUp(){
		studentsDAO = new StudentsDAO(jdbcTemplate, studentRowMapper);
		coursesDAO = new CoursesDAO(jdbcTemplate, courseRowMapper, studentRowMapper);
		groupsDAO = new GroupsDAO(jdbcTemplate, groupRowMapper);
	}



	@Test
	@Order(1)
	void shouldRetrieveCourses(){
		List<Course> list = coursesDAO.getAll();
		assertEquals(5, list.size());
	}

	@Test
	@Order(2)
	void shouldCreateCourse(){
		coursesDAO.create(new Course("course", "course"));
		assertEquals(6, coursesDAO.getAll().size());
	}

	@Test
	@Order(3)
	void shouldGetCourseById(){
		Course course = coursesDAO.get(1).get();
		assertEquals(1, course.getId());
	}

	@Test
	@Order(4)
	void shouldUpdateCourse(){
		Course course = coursesDAO.get(1).get();
		course.setName("Geography");
		coursesDAO.update(course);
		assertEquals("Geography", coursesDAO.get(1).get().getName());

	}

	@Test
	@Order(5)
	void shouldDeleteCourse(){
		coursesDAO.delete(1);
		List<Course> list = coursesDAO.getAll();
		assertEquals(4, list.size());
	}


	@Test
	@Order(6)
	void shouldGetAllStudentsRelatedToCourse(){
		studentsDAO.addCourse(1, 2);
		studentsDAO.addCourse(2, 2);
		List<Student> numberOfStudents = coursesDAO.getStudents(2);
		assertEquals(2, numberOfStudents.size());
	}

	@Test
	@Order(7)
	void shouldGetAllCoursesForStudent(){
		studentsDAO.addCourse(1, 2);
		int number = coursesDAO.getCoursesForStudent(1).size();
		assertEquals(1, number);
	}

	@Configuration
	static class StudentsDAOTestConfiguration {
		@Bean
		StudentRowMapper rowMapper(){
			return new StudentRowMapper();
		}

		@Bean
		CourseRowMapper courseRowMapper(){
			return new CourseRowMapper();
		}

		@Bean
		GroupRowMapper groupRowMapper(){
			return new GroupRowMapper();
		}
	}
}
