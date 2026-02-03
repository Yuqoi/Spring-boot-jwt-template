
package com.example.jwt.utils;

import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectToStringConverter {
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
