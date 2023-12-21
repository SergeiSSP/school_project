package service;

import com.foxminded.senkiv.school_project.database.CoursesDAO;
import com.foxminded.senkiv.school_project.model.Course;
import com.foxminded.senkiv.school_project.service.CourseService;
import com.foxminded.senkiv.school_project.service.CourseServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes={CourseServiceImpl.class})
class CourseServiceImplTest {
	@MockBean
	private CoursesDAO coursesDAO;

	private final CourseService courseService;

	@Autowired
	public CourseServiceImplTest(CourseService courseService) {
		this.courseService = courseService;
	}

	@Test
	void shouldCreateCourse(){
		Course course = new Course();
		courseService.create(course);
		verify(coursesDAO).create(any(Course.class));
	}

	@Test
	void shouldGetCourse(){
		courseService.get(anyInt());
		verify(coursesDAO).get(anyInt());
	}

	@Test
	void shouldGetAllCourses(){
		courseService.getAll();
		verify(coursesDAO).getAll();
	}

	@Test
	void shouldUpdateCourse(){
		courseService.update(new Course());
		verify(coursesDAO).update(any(Course.class));
	}

	@Test
	void shouldDeleteCourse(){
		courseService.delete(anyInt());
		verify(coursesDAO).delete(anyInt());
	}

	@Test
	void shouldGetStudentsOnCourse(){
		courseService.getStudents(anyInt());
        verify(coursesDAO).getStudents(anyInt());
	}

	@Test
	void shouldGetCoursesOfStudent(){
		courseService.getStudentCourses(anyInt());
		verify(coursesDAO).getCoursesForStudent(anyInt());
	}

	@Test
	void shouldBatchCreateCourses() {
        List<Course> courses = new ArrayList<>();
        courseService.batchCreate(courses);
        verify(coursesDAO).batchCreate(courses);
    }
}
