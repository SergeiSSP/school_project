package com.foxminded.senkiv.school_project.database.mapper;


import com.foxminded.senkiv.school_project.model.Group;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GroupRowMapper implements RowMapper<Group> {
    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
        var group = new Group();
        group.setId(rs.getInt("group_id"));
        group.setName(rs.getString("group_name"));
        return group;
    }
}
