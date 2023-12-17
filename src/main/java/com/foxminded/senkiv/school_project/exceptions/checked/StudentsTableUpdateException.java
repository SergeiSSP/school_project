package com.foxminded.senkiv.school_project.exceptions.checked;

import com.foxminded.senkiv.school_project.model.Student;
import org.springframework.dao.DataAccessException;

public class StudentsTableUpdateException extends DataAccessException {
	public StudentsTableUpdateException(Student student) {
		super(String.format("Failed to update profile of %s %s (id = %d)",
			student.getFirstName(),
			student.getLastName(),
			student.getId()));
	}

	public StudentsTableUpdateException(Student student, Throwable cause) {
		super(String.format("Failed to update profile of %s %s (id = %d)",
			student.getFirstName(),
			student.getLastName(),
			student.getId()), cause);
	}

	public StudentsTableUpdateException(int id, Throwable cause){
		super(String.format("Failed to update profile with id - %d", id),cause);
	}

	public StudentsTableUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
