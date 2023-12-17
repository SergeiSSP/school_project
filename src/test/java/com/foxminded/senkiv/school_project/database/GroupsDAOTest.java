package com.foxminded.senkiv.school_project.database;

import com.foxminded.senkiv.school_project.database.mapper.CourseRowMapper;
import com.foxminded.senkiv.school_project.database.mapper.GroupRowMapper;
import com.foxminded.senkiv.school_project.database.mapper.StudentRowMapper;
import com.foxminded.senkiv.school_project.model.Group;
import com.foxminded.senkiv.school_project.seeder.StudentGenerator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"classpath:initial_table_schema.sql", "classpath:insert_test_data.sql"},
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GroupsDAOTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	StudentRowMapper studentRowMapper;
	@Autowired
	GroupRowMapper groupRowMapper;
	@Autowired
	CourseRowMapper courseRowMapper;
	@Autowired
	StudentGenerator generator;
	private  StudentsDAO studentsDAO;
	private GroupsDAO groupsDAO;

	@BeforeEach
	void setUp(){
		studentsDAO = new StudentsDAO(jdbcTemplate, studentRowMapper);
		groupsDAO = new GroupsDAO(jdbcTemplate, groupRowMapper);
	}


	@Test
	@Order(1)
	void shouldRetrieveAllGroup(){
		assertEquals(5, groupsDAO.getAll().size());
	}

	@Test
	@Order(2)
	void shouldCreateGroup(){
		groupsDAO.create(new Group("New Group"));
		assertEquals(6, groupsDAO.getAll().size());
	}

	@Test
	@Order(3)
	void shouldUpdateGroup(){
		Group group = groupsDAO.get(1).get();
		group.setName("Updated Name");
		groupsDAO.update(group);
		assertEquals("Updated Name", groupsDAO.get(1).get().getName());
	}

	@Test
	@Order(4)
	void shouldDeleteGroup(){
		groupsDAO.delete(6);
		assertEquals(5, groupsDAO.getAll().size());
	}

	@Test
	@Order(5)
	void shouldFilterGroupsByNumberOfStudents(){
		studentsDAO.addGroup(1,1);
		studentsDAO.addGroup(2,1);
		studentsDAO.addGroup(3,1);
		assertEquals(1, groupsDAO.filterQuantity(3).size());
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

		@Bean
		StudentGenerator generator(){
			return new StudentGenerator();
		}
	}
}
