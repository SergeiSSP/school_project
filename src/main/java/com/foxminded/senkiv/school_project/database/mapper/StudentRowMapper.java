package com.foxminded.senkiv.school_project.database.mapper;

import com.foxminded.senkiv.school_project.model.Student;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class StudentRowMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        var student = new Student();
        student.setId(rs.getInt("student_id"));
        student.setGroupId(rs.getInt("group_id"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        return student;
    }
}
