package com.foxminded.senkiv.school_project.exceptions.checked;

import org.springframework.dao.DataAccessException;

public class StudentsCoursesTableUpdateException extends DataAccessException {
	private static final String MESSAGE = "Failed to add student with id %d to group with id %d";
	public StudentsCoursesTableUpdateException(int studentId, int courseId) {
		super(String.format(MESSAGE, studentId, courseId));
	}

	public StudentsCoursesTableUpdateException(int studentId, int courseId, Throwable cause) {
		super(String.format(MESSAGE, studentId, courseId), cause);
	}

	public StudentsCoursesTableUpdateException(String message, Throwable cause){
		super(message, cause);
	}
}
