package com.foxminded.senkiv.school_project.exceptions.checked;

import com.foxminded.senkiv.school_project.model.Group;
import org.springframework.dao.DataAccessException;

public class GroupsTableUpdateException extends DataAccessException {
	private static final String MESSAGE = "Failed to update group %d. %s.";
	public GroupsTableUpdateException(Group group, Throwable cause) {
		super(String.format(MESSAGE, group.getId(), group.getName()), cause);
	}

	public GroupsTableUpdateException(int id, Throwable cause){
		super(String.format(MESSAGE, id, ""), cause);
	}

	public GroupsTableUpdateException(String message, Throwable cause){
		super(message, cause);
	}
	public GroupsTableUpdateException(String message) {
        super(message);
    }
}
