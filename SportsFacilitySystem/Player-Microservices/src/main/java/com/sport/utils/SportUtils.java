package com.sport.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SportUtils {

	private SportUtils() {

	}

	public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus) {
		return new ResponseEntity<String>("{\"message\":\"" + responseMessage + "\"}", httpStatus);
	}

}
