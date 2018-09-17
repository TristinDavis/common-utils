package com.cweijan.util;

import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class JsonUtils{

    private static ObjectMapper mapper = new ObjectMapper();
    private static ObjectMapper withEmptyMapper = new ObjectMapper();

    static{
        mapper.setSerializationInclusion(Inclusion.NON_EMPTY);
        withEmptyMapper.setSerializationInclusion(Inclusion.ALWAYS);
        //		 反序列化忽略json未知字段
        mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    /**
     将对象转换成json
     */
    public static String objectToJson(Object object){

        String json = null;
        try{
            json = mapper.writeValueAsString(object);
        } catch(Exception e){
            logger.error("objectToJson error:", e);
        }

        return json;
    }

    public static String objectToJsonWithEmpty(Object object){

        String json = null;
        try{
            json = withEmptyMapper.writeValueAsString(object);
        } catch(Exception e){
            logger.error("objectToJson error:", e);
        }

        return json;
    }

    /**
     将json转成List

     @param json json字符串
     @param valueType list泛型
     */
    public static <T> List<T> jsonToList(String json, Class<T> valueType){

        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, valueType);
        try{
            return mapper.readValue(json, javaType);
        } catch(Exception e){
            logger.error("jsonToList error:" + e.getMessage(), e);
        }

        return null;
    }

    /**
     将json转成指定的类对象
     */
    public static <T> T jsonToObject(String json, Class<T> type){

        T object = null;
        try{
            object = mapper.readValue(json, type);
        } catch(Exception e){
            logger.error("jsonToObject error:" + e.getMessage(), e);
        }
        return object;

    }

}
