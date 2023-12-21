package service;

import com.foxminded.senkiv.school_project.database.StudentsDAO;
import com.foxminded.senkiv.school_project.model.Student;
import com.foxminded.senkiv.school_project.service.StudentService;
import com.foxminded.senkiv.school_project.service.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {StudentServiceImpl.class})
class StudentServiceImplTest {
	@MockBean
	private StudentsDAO studentsDAO;
	private final StudentService studentService;

	@Autowired
	public StudentServiceImplTest(StudentServiceImpl studentService){
		this.studentService = studentService;
	}

	@Test
	void shouldCreateStudent() {
        Student student = new Student();
        studentService.create(student);
        verify(studentsDAO).create(any(Student.class));
    }

	@Test
	void shouldGetStudent() {
        int id = 1;
        studentService.get(id);
        verify(studentsDAO).get(id);
    }

	@Test
	void shouldUpdateStudent(){
		studentService.create(new Student());
        studentService.update(new Student());
		InOrder inOrder = Mockito.inOrder(studentsDAO);
		inOrder.verify(studentsDAO).create(any(Student.class));
        inOrder.verify(studentsDAO).update(any(Student.class));
	}

	@Test
	void shouldDeleteStudent(){
		Student student = new Student();
		studentService.delete(student.getId());
        verify(studentsDAO).delete(student.getId());
	}

	@Test
	void shouldAddGroup() {
        int groupId = 1;
        int studentId = 1;
        studentService.addGroup(groupId, studentId);
        verify(studentsDAO).addGroup(groupId, studentId);
    }

	@Test
	void shouldAddCourse() {
        int studentId = 1;
        int courseId = 1;
        studentService.addCourse(studentId, courseId);
        verify(studentsDAO).addCourse(studentId, courseId);
    }

	@Test
	void shouldRemoveCourse(){
		int studentId = 1;
        int courseId = 1;
        studentService.deleteCourse(studentId, courseId);
        verify(studentsDAO).deleteCourse(studentId, courseId);
	}

	@Test
	void shouldCreateListOfStudents(){
		List<Student> students = new ArrayList<>();
        students.add(new Student());
        students.add(new Student());
        studentService.batchCreate(students);
        verify(studentsDAO).batchCreate(students);
	}
}
