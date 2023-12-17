package com.foxminded.senkiv.school_project.exceptions.checked;

import com.foxminded.senkiv.school_project.model.Course;
import org.springframework.dao.DataAccessException;


public class CoursesTableUpdateException extends DataAccessException{
	private static final String MESSAGE = "Failed to update course %d. %s.";
	public CoursesTableUpdateException(Course course, Throwable cause){
        super(String.format(MESSAGE, course.getId(), course.getName()), cause);
	}

	public CoursesTableUpdateException(int id, Throwable cause){
		super(String.format(MESSAGE, id, ""), cause);
	}

	public CoursesTableUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

	public CoursesTableUpdateException(String message){
		super(message);
	}
}
