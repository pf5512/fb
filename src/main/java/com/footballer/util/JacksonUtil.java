package com.footballer.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JacksonUtil {

	private static Logger log = LoggerFactory.getLogger(JacksonUtil.class);
	
	private static final ObjectMapper webMapper = new ObjectMapper();
	static {
		webMapper.setSerializationInclusion(Include.NON_NULL).setSerializationInclusion(Include.NON_EMPTY);
		webMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		webMapper.setDateFormat(new SimpleDateFormat(DateUtil.LONG_DATE_TIME));
	}

	
	public static String toJson(Object obj) {
		String json = null;
		try {
			json = webMapper.writeValueAsString(obj);
		} catch(JsonProcessingException e) {
			log.error("object to json error",e);
		}
		return json;
	}
	
	
	public static Map<String, Object> jsonToMap(String json) {
		Map<String, Object> map = null;
		try {
			map = webMapper.readValue(json, Map.class);
		} catch(IOException e) {
			log.error("json to map error",e);
		} 
		return map;
	}
	
	public static <T> T jsonToObject(String json,Class<T> T) {
		try {
			return  webMapper.readValue(json, T);
		} catch(IOException e) {
			log.error("json to db object error",e);
			return null;
		}
	}
	
	public static <T> T jsonToObject(String json,JavaType type) {
		try {
			return  webMapper.readValue(json, type);
		} catch(IOException e) {
			log.error("json to db object error",e);
			return null;
		}
	}
	
}
