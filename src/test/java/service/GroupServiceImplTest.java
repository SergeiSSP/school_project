package service;

import com.foxminded.senkiv.school_project.database.GroupsDAO;
import com.foxminded.senkiv.school_project.model.Group;
import com.foxminded.senkiv.school_project.service.GroupService;
import com.foxminded.senkiv.school_project.service.GroupServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes={GroupServiceImpl.class})
class GroupServiceImplTest {
	@MockBean
	private GroupsDAO groupDAO;
	private final GroupService groupService;

	@Autowired
    public GroupServiceImplTest(GroupService groupService) {
        this.groupService = groupService;
    }

	@Test
	void shouldCreateGroup(){
		groupService.create(new Group());
		verify(groupDAO).create(any(Group.class));
	}

	@Test
	void shouldGetGroup(){
		groupService.get(anyInt());
		verify(groupDAO).get(anyInt());
	}

	@Test
	void shouldGetAll(){
		groupService.getAll();
		verify(groupDAO).getAll();
	}

	@Test
	void shouldUpdateGroup(){
		groupService.update(new Group());
		verify(groupDAO).update(any(Group.class));
	}

	@Test
	void shouldDeleteGroup() {
        groupService.delete(anyInt());
        verify(groupDAO).delete(anyInt());
    }

	@Test
	void shouldCreateGroupInBatch(){
		groupService.batchCreate(new ArrayList<>());
		verify(groupDAO).batchCreate(anyList());
	}

	@Test
	void shouldFilterGroupsByAmountOfStudents(){
		groupService.filterByStudentQuantity(anyInt());
		verify(groupDAO).filterQuantity(anyInt());
	}
}
