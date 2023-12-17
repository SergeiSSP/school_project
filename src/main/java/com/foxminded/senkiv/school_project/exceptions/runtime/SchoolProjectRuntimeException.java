package com.foxminded.senkiv.school_project.exceptions.runtime;

public class SchoolProjectRuntimeException extends RuntimeException {
    public SchoolProjectRuntimeException(Throwable cause) {
        super(cause);
    }

    public SchoolProjectRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

	public SchoolProjectRuntimeException(String message){
		super(message);
	}
}
