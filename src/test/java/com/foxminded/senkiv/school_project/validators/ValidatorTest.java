package com.foxminded.senkiv.school_project.validators;

import com.foxminded.senkiv.school_project.exceptions.runtime.SchoolProjectRuntimeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {
	@Test
	void shouldThrowSchoolProjectRuntimeExceptionIfNullUri() {
		Exception exception = assertThrows(SchoolProjectRuntimeException.class, () -> Validator.checkFile(null));

		String expectedMessage = "Invalid file link";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void shouldThrowSchoolProjectRuntimeExceptionIfEmptyUri() {
		Exception exception = assertThrows(SchoolProjectRuntimeException.class, () -> Validator.checkFile(""));
		String expectedMessage = "Invalid file link";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void shouldThrowNothing() {
		Validator.checkFile("valid/file/uri");
	}
}
