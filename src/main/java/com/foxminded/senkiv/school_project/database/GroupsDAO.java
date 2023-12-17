package com.foxminded.senkiv.school_project.database;

import com.foxminded.senkiv.school_project.database.mapper.GroupRowMapper;
import com.foxminded.senkiv.school_project.exceptions.checked.GroupsTableUpdateException;
import com.foxminded.senkiv.school_project.model.Group;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.*;

@Repository
public class GroupsDAO implements DAO<Group>{
	private final JdbcTemplate jdbcTemplate;
	private final GroupRowMapper rowMapper;

	private static final String SELECT_BY_ID_STATEMENT = "SELECT * FROM groups WHERE group_id = ? LIMIT 1;";
	private static final String SELECT_ALL_STATEMENT = "SELECT * FROM groups;";
	private static final String INSERT_STATEMENT = "INSERT INTO groups(group_name) VALUES (?);";
	private static final String UPDATE_STATEMENT = "UPDATE groups Set group_name = ? WHERE group_id = ?;";
	private static final String DELETE_STATEMENT = "DELETE FROM groups WHERE group_id = ?;";

	private static final String SELECT_GROUPS_BY_QUANTITY = """
		SELECT groups.group_id, groups.group_name, COUNT(student_id) AS quantity
		FROM students
		JOIN groups ON students.group_id = groups.group_id
		GROUP BY groups.group_id
		HAVING COUNT(students.student_id) <= ?
		ORDER BY quantity DESC ;""";

	public GroupsDAO(JdbcTemplate jdbcTemplate, GroupRowMapper rowMapper) {
		this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

	@Override
	public Optional<Group> get(int id) {
		try {
			var list = jdbcTemplate.query(SELECT_BY_ID_STATEMENT, rowMapper, id);
			return Optional.ofNullable(list.get(0));
		}catch(EmptyResultDataAccessException e){
			return Optional.empty();
		}
	}

	@Override
	public List<Group> getAll() {
		try {
			return jdbcTemplate.query(SELECT_ALL_STATEMENT, rowMapper);
		}catch(EmptyResultDataAccessException e){
			return new ArrayList<>();
		}
	}

	@Override
	public void create(Group group) {
		try {
			jdbcTemplate.update(INSERT_STATEMENT, group.getName());
		}catch(EmptyResultDataAccessException e){
			throw new GroupsTableUpdateException(group, e);
		}
	}

	@Override
	public void update(Group group) {
		try {
			jdbcTemplate.update(UPDATE_STATEMENT, group.getName(), group.getId());
		}catch(EmptyResultDataAccessException e){
			throw new GroupsTableUpdateException(group, e);
		}
	}

	@Override
	public void delete(int id) {
		try {
			jdbcTemplate.update(DELETE_STATEMENT, id);
		}catch(EmptyResultDataAccessException e){
			throw new GroupsTableUpdateException(id, e);
		}
	}

	public Map<Group, Integer> filterQuantity(int quantity) {
		Map<Group, Integer> result = new HashMap<>();
		RowMapper<Map.Entry<Group, Integer>> entryRowMapper = (rs, rowNum) -> {
			var group = new Group();
			group.setId(rs.getInt("group_id"));
			group.setName(rs.getString("group_name"));
			return Map.entry(group, rs.getInt("quantity"));
		};
		try {
			List<Map.Entry<Group, Integer>> list = jdbcTemplate.query(SELECT_GROUPS_BY_QUANTITY, entryRowMapper, quantity);
			list.forEach(entry -> result.put(entry.getKey(), entry.getValue()));
			return result;
		} catch (EmptyResultDataAccessException e) {
			return new HashMap<>();
		}
	}

	@Override
	@Transactional
	public void batchCreate(List<Group> list){
		jdbcTemplate.batchUpdate(
			INSERT_STATEMENT,
			list,
			50,
			(PreparedStatement ps, Group group)-> ps.setString(1, group.getName()));
	}
}
