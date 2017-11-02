package com.cweijan.util;


import java.util.List;

import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils{

	private static ObjectMapper mapper = new ObjectMapper();
	static{
		//不序列化Null column
		mapper.getSerializationConfig().withSerializationInclusion(Inclusion.NON_NULL);
		
		//忽略json未知字段
		mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	private static Logger logger=LoggerFactory.getLogger(JsonUtils.class);
	
	/**
	 * 将对象转换成json
	 */
	public static String objectToJson(Object object) {
		
		String json =null;
		try {
			json = mapper.writeValueAsString(object);
		}  catch (Exception e) {
			logger.error("objectToJson error:",e);
		}
		
		return json;
	}
	
	/**
	 * 将json转成List
	 * @param json
	 * @param listType 要转换成的list类型
	 * @param valueType list里面的值类型
	 * @return
	 */
	public static <T> List<T> jsonToList(String json,Class<T> valueType){
		
		
		JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, valueType);
		try {
			return mapper.readValue(json, javaType);
		} catch (Exception e) {
			logger.error("jsonToList error:"+e.getMessage(),e);
		}
		
		return null;
	}
	
	/**
	 * 将json转成指定的类对象
	 */
	public static <T> T jsonToObject(String json,Class<T> type) {
		
		
		T object=null;
		try {
			object = mapper.readValue(json,type);
		} catch (Exception e) {
			logger.error("jsonToObject error:"+e.getMessage(),e);
		}
		return object;
		
	}
	
}
