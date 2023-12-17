package com.foxminded.senkiv.school_project.seeder;

import com.foxminded.senkiv.school_project.database.*;
import com.foxminded.senkiv.school_project.exceptions.runtime.SchoolProjectRuntimeException;
import com.foxminded.senkiv.school_project.model.Course;
import com.foxminded.senkiv.school_project.model.Group;
import com.foxminded.senkiv.school_project.model.Student;
import com.foxminded.senkiv.school_project.utils.Logging;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@Component
public class DBStarter {
	Logger logger = Logging.logger;
    private final CoursesDAO courseDAO;
	private final GroupsDAO groupsDAO;
	private final StudentsDAO studentDAO;
	private final StudentGenerator studentGenerator;
	private final Random random = new Random();

    public DBStarter(CoursesDAO courseDAO, GroupsDAO groupsDAO, StudentsDAO studentDAO, StudentGenerator studentGenerator) {
        this.courseDAO = courseDAO;
        this.groupsDAO = groupsDAO;
        this.studentDAO = studentDAO;
        this.studentGenerator = studentGenerator;
    }

    public void startUp() {
        try {
            generateCourses();
			generateGroups();
			generateStudents();
			addStudentsToGroups();
			addStudentToCourse();
			logger.info("Set up of database is finished.");
        }catch(Exception e){
            throw new SchoolProjectRuntimeException(e);
        }
    }

    private void generateCourses() {
		List<Course> courses = new ArrayList<>();
        for(Courses course:Courses.values()){
			courses.add(new Course(course.getName(), course.getDescription()));
		}
		courseDAO.batchCreate(courses);
    }

	private void generateGroups(){
		List<Group> list = new ArrayList<>();
		int numberOfGroups = 10;
		for(int i = 0; i < numberOfGroups; i++){
			StringBuilder sb = new StringBuilder();
				sb.append(randomAlphabetic(2)).append("-").append(randomNumeric(2));
				list.add(new Group(sb.toString()));
		}
		groupsDAO.batchCreate(list);
	}

	private void generateStudents(){
		List<Student> list = new ArrayList<>();
		int numberOfStudent = 200;
		for(int i = 0; i < numberOfStudent; i++){
			list.add(studentGenerator.getStudent());
		}
		studentDAO.batchCreate(list);
	}

	private void addStudentsToGroups(){
		List<Student> students = studentDAO.getAll();
		List<Group> groups = groupsDAO.getAll();
		students.forEach(
			student -> {
				int groupId = random.nextInt(0, groups.size()+1);
				if (groupId > 0) {
					studentDAO.addGroup(groupId, student.getId());
				}
			}
		);
	}

	private void addStudentToCourse(){
		int maximumNumberOfCourses = 3;
		List<Student> students = studentDAO.getAll();
		List<Course> courses = courseDAO.getAll();
		students.forEach(
				student-> {
					int numberOfCourses = random.nextInt(1, maximumNumberOfCourses+1);
					ArrayList<Integer> courseAssigned = new ArrayList<>();
					for(int i = 0; i < numberOfCourses; i++){
						int courseId = random.nextInt(1, courses.size()+1);
						if(!courseAssigned.contains(courseId)) {
							studentDAO.addCourse(student.getId(), courseId);
							courseAssigned.add(courseId);
						}
					}
				}
			);
	}
}
