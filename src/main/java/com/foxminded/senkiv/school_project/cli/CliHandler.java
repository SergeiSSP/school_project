package com.foxminded.senkiv.school_project.cli;


import com.foxminded.senkiv.school_project.database.CoursesDAO;
import com.foxminded.senkiv.school_project.database.GroupsDAO;
import com.foxminded.senkiv.school_project.database.StudentsDAO;
import com.foxminded.senkiv.school_project.model.Course;
import com.foxminded.senkiv.school_project.model.Student;
import com.foxminded.senkiv.school_project.validators.Validator;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static com.foxminded.senkiv.school_project.validators.Validator.validateInput;

@Component
public class CliHandler {
	private final GroupsDAO groupsDAO;
	private final StudentsDAO studentsDAO;
	private final CoursesDAO coursesDAO;
	private String choices;
	private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static final Logger logger = LogManager.getLogger(CliHandler.class);
	private static final String OUTPUT_ENTRY = "%n%d. %s";

	public CliHandler(GroupsDAO groupsDAO, StudentsDAO studentsDAO, CoursesDAO coursesDAO) {
		this.groupsDAO = groupsDAO;
		this.studentsDAO = studentsDAO;
		this.coursesDAO = coursesDAO;
	}

	@PostConstruct
	private void postConstruct(){
		StringBuilder sb = new StringBuilder();
		for(Choices value: Choices.values()){
			sb.append(String.format(OUTPUT_ENTRY, value.getNumber(), value.getAction()));
		}
		choices = sb.append("\nChoose action to perform and press <Enter>").toString();
	}


	public void cliApplication() throws IOException {
		while (true) {
			logger.info(choices);
			int validatedChoice = receiveChoice();
			if(validatedChoice == 0){
				break;
			}
			switch(validatedChoice) {
				case 1 -> filterGroupsByStudentsNumber();
				case 2 -> filterStudentsByCourse();
				case 3 -> createStudent();
				case 4 -> deleteStudent();
				case 5 -> addStudentToCourse();
				case 6 -> removeStudentFromCourse();
			}
		}
	}

	private void filterGroupsByStudentsNumber()throws IOException{
		logger.info("Enter required amount of students.");
		StringBuilder sb = new StringBuilder();
		int quantity = Integer.parseInt(br.readLine());
		groupsDAO.filterQuantity(quantity)
                .forEach((key, value) -> sb.append(String.format("%n%s contains %d students", key, value)));
		logger.info(sb);
	}

	private void filterStudentsByCourse() throws IOException{
		StringBuilder sb = new StringBuilder();
		List<Course> list = coursesDAO.getAll();
		list.forEach(course -> sb.append(String.format(OUTPUT_ENTRY, course.getId(), course.getName())));
		logger.info(sb);
		logger.info("Enter id of required course to find all related students");
		int courseId;
		while(true){
			courseId = Integer.parseInt(br.readLine());
			if(courseId > 0 && courseId <= list.size()){
				break;
			} else {
				logger.info("Try once more.");
			}
		}
		List<Student> studentsOnCourse = coursesDAO.getStudents(courseId);
		sb.setLength(0);
		studentsOnCourse.forEach(student ->
				sb.append(String.format("%n%d. %s %s", student.getId(), student.getFirstName(), student.getLastName())));
		logger.info(sb);
	}

    private void createStudent() throws IOException {
        String firstName = getValidatedInput(br,"Enter first name", Validator::validateInput);
        String lastName = getValidatedInput(br, "Enter last name", Validator::validateInput);
        studentsDAO.create(new Student(firstName, lastName));
    }


	 private void deleteStudent()throws IOException{
		List<Student> all = studentsDAO.getAll();
		 logger.info("Enter id of student that you want to delete");
		 int id;
		 while(true){
			  id = Integer.parseInt(br.readLine());
			  if(id > 0 && id <= all.size()){
				  studentsDAO.delete(id);
				  break;
			  } else {
				  logger.info("Enter valid id");
			  }
		 }
	 }

	 private void addStudentToCourse()throws IOException {
		 List<Course> list = coursesDAO.getAll();
		 StringBuilder sb = new StringBuilder();
		 list.forEach(course -> sb.append(String.format(OUTPUT_ENTRY, course.getId(), course.getName())));
		 logger.info(sb);
		 logger.info("Enter the number of course you want to assign");
		 int courseId = Integer.parseInt(br.readLine());
		 logger.info("Enter your student id");
		 int studentId = Integer.parseInt(br.readLine());
		 if (coursesDAO.getStudents(courseId).stream().anyMatch(student -> student.getId() == studentId)) {
			 logger.info("This student is already assigned to this course.");
		 } else {
			 studentsDAO.addCourse(studentId, courseId);
		 }
	 }

	 private void removeStudentFromCourse()throws IOException{
		StringBuilder sb = new StringBuilder();
		logger.info("Enter id of student.");
		int studentId;
		while(true) {
			studentId = Integer.parseInt(br.readLine());
			if(studentsDAO.get(studentId).isPresent()){
				break;
			}
		}
		List<Course> list = coursesDAO.getCoursesForStudent(studentId);
		list.forEach(course ->
				sb.append(String.format(OUTPUT_ENTRY, course.getId(), course.getName()))
			);
		logger.info(sb);
		logger.info("Enter id of the course to remove.");
		while(true){
			int number = Integer.parseInt(br.readLine());
			Optional<Course> toDelete = list.stream()
				.filter( course -> course.getId() == number)
				.findAny();
			if(toDelete.isPresent()){
				studentsDAO.deleteCourse(studentId, number);
				break;
			} else {
				logger.info("Try again.");
			}
		}
	 }

	 private int receiveChoice() throws IOException {
		 while (true) {
			 String choice = br.readLine();
			 if(validateInput(choice)) {
				 int validatedChoice = Integer.parseInt(choice);
				 if (validatedChoice >= 0 && validatedChoice < Choices.values().length) {
					 return validatedChoice;
				 }
			 }
		 }
	 }

	public static String getValidatedInput(BufferedReader bufferedReader, String message, Predicate<String> validator) throws IOException {
		String input = null;
		boolean isValidInput = false;
		while (!isValidInput) {
			logger.info(message);
			input = bufferedReader.readLine();

			if (validator.test(input)) {
				isValidInput = true;
			} else {
				logger.info("Try again");
			}
		}

		return input;

	}
}