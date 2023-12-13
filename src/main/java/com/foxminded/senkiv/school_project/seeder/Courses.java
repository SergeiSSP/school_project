package com.foxminded.senkiv.school_project.seeder;

public enum Courses {
	MATHS("Maths", "Description for Maths"),
	PHYSICS("Physics", "Description for Physics"),
	BIOLOGY("Biology", "Description for Biology"),
	GEOGRAPHY("Geography", "Description for Geography"),
	INFORMATICS("Informatics", "Description for Informatics"),
	ENGLISH("English", "Description for English"),
	UKRAINIAN("Ukrainian", "Description for Ukrainian"),
	GEOMETRY("Geometry", "Description for Geometry"),
	CHEMISTRY("Chemistry", "Description for Chemistry"),
	MUSIC("Music", "Description for Music");

	private final String name;
	private final String description;

	Courses(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
