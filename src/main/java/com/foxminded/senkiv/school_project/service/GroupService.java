package com.foxminded.senkiv.school_project.service;

import com.foxminded.senkiv.school_project.model.Group;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GroupService {
	Optional<Group> get(int id);
	List<Group> getAll();
	void create(Group group);
	void batchCreate(List<Group> list);
	void update(Group group);
	void delete(int id);

	Map<Group, Integer> filterByStudentQuantity(int quantity);
}
