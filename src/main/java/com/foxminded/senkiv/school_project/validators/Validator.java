package com.foxminded.senkiv.school_project.validators;

import com.foxminded.senkiv.school_project.exceptions.runtime.SchoolProjectRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Validator {
	static Logger logger = LogManager.getLogger();
    private Validator(){}
    public static void checkFile(String uri){
        if(uri == null || uri.trim().isEmpty()){
            throw new SchoolProjectRuntimeException("Invalid file link");
        }
    }

	public static boolean validateInput(String input) {
		if(input == null || input.trim().isEmpty()){
			logger.info("Invalid input. Try again");
			return false;
		}
		return true;
	}
}
