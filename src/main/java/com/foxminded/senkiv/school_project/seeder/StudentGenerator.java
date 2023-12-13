package com.foxminded.senkiv.school_project.seeder;

import com.foxminded.senkiv.school_project.model.Student;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class StudentGenerator {
	Random random = new Random();
	private static final String[] FIRST_NAMES = {
		"James", "Mary", "John", "Patricia", "Robert", "Jennifer",
		"Michael", "Linda", "William", "Elizabeth", "David", "Barbara",
		"Richard", "Susan", "Joseph", "Jessica", "Thomas", "Sarah",
		"Charles", "Karen"
	};

	private static final String[] LAST_NAMES = {
		"Smith", "Johnson", "Williams", "Brown", "Jones",
		"Garcia", "Miller", "Davis", "Rodriguez", "Martinez",
		"Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson",
		"Thomas", "Taylor", "Moore", "Jackson", "Martin"
	};

	public Student getStudent() {
		String name = getName();
		String surname = getSurname();
		return new Student(name, surname);
	}

	private String getName() {
		return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
	}

	private String getSurname() {
		return LAST_NAMES[random.nextInt(LAST_NAMES.length)];
	}
}