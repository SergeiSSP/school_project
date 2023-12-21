package com.foxminded.senkiv.school_project.service;

import com.foxminded.senkiv.school_project.database.GroupsDAO;
import com.foxminded.senkiv.school_project.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {
	private final GroupsDAO groupsDao;

	@Autowired
    public GroupServiceImpl(GroupsDAO groupsDao) {
        this.groupsDao = groupsDao;
    }

	@Override
	public Optional<Group> get(int id) {
		return groupsDao.get(id);
	}

	@Override
	public void create(Group group) {
		groupsDao.create(group);
	}

	@Override
	public void batchCreate(List<Group> list) {
		groupsDao.batchCreate(list);
	}

	@Override
	public void update(Group group) {
		groupsDao.update(group);
	}

	@Override
	public void delete(int id) {
		groupsDao.delete(id);
	}

	@Override
	public List<Group> getAll() {
		return groupsDao.getAll();
	}

	@Override
	public Map<Group, Integer> filterByStudentQuantity(int quantity) {
		return groupsDao.filterQuantity(quantity);
	}
}
