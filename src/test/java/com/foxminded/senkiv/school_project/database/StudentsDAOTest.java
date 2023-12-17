package com.foxminded.senkiv.school_project.database;

import com.foxminded.senkiv.school_project.database.mapper.CourseRowMapper;
import com.foxminded.senkiv.school_project.database.mapper.GroupRowMapper;
import com.foxminded.senkiv.school_project.database.mapper.StudentRowMapper;
import com.foxminded.senkiv.school_project.model.Student;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"classpath:initial_table_schema.sql", "classpath:insert_test_data.sql"},
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentsDAOTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    StudentRowMapper studentRowMapper;
    @Autowired
    GroupRowMapper groupRowMapper;
    @Autowired
    CourseRowMapper courseRowMapper;
    private  StudentsDAO studentsDAO;
    private GroupsDAO groupDAO;
    private CoursesDAO courseDAO;

    @BeforeEach
    void setUp(){
        studentsDAO = new StudentsDAO(jdbcTemplate, studentRowMapper);
        courseDAO = new CoursesDAO(jdbcTemplate, courseRowMapper, studentRowMapper);
        groupDAO = new GroupsDAO(jdbcTemplate, groupRowMapper);
    }

    @Test
    @Order(1)
    void shouldGetAllStudents(){
        assertEquals(10, studentsDAO.getAll().size());
    }


    @Test
    @Order(2)
    void shouldCreateStudent() {
        studentsDAO.create(new Student("Serhii", "Senkiv"));
        var student = studentsDAO.get(11);
        assertEquals("Serhii", student.get().getFirstName());
        assertEquals("Senkiv", student.get().getLastName());
        assertEquals(11, studentsDAO.getAll().size());

    }

    @Test
    @Order(3)
    void shouldDeleteStudent(){
        studentsDAO.delete(11);
        assertEquals(10, studentsDAO.getAll().size());
    }

    @Test
    @Order(4)
    void shouldAddStudentToGroup(){
        studentsDAO.addGroup(1, 1);
        var group = studentsDAO.get(1).get().getGroupId();
        assertEquals(1, group);
    }

    @Test
    @Order(5)
    void shouldAddCourse(){
        studentsDAO.addCourse(1, 1);
        var list = courseDAO.getCoursesForStudent(1);
        assertEquals("first", list.get(0).getName());
    }

    @Test
    @Order(6)
    void shouldUpdateStudent(){
        var student = studentsDAO.get(1).get();
        student.setFirstName("second");
        studentsDAO.update(student);
        assertEquals("second", studentsDAO.get(1).get().getFirstName());

    }

    @Test
    @Order(7)
    void shouldTrowRuntimeExceptionIfNotExist() {
        assertThrows(RuntimeException.class, () -> studentsDAO.get(154156));
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
