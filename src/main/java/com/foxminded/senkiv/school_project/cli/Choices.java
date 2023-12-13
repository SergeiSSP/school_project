package com.foxminded.senkiv.school_project.cli;

public enum Choices {
	FILTER_GROUPS_BY_STUDENT(1, "Find all groups with less or equal students’ number."),
	FILTER_STUDENTS_BY_COURSE(2, "Find all students related to the course with the given name."),
	ADD_STUDENT(3, "Add a new student."),
	DELETE_STUDENT(4, "Delete a student by the STUDENT_ID."),
	ASSIGN_STUDENT_TO_COURSE(5, "Add a student to the course (from a list)."),
	REMOVE_STUDENT_FROM_COURSE(6, "Remove the student from one of their courses."),
	EXIT(0, "Exit");

	private final int number;
	private final String action;
	Choices(int number, String action){
		this.number = number;
		this.action = action;
	}

	public int getNumber(){
		return this.number;
	}

	public String getAction(){
		return this.action;
	}

}
