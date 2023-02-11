package help.lixin.xxl.job.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JacksonUtils {

    private JacksonUtils() {}

    public final static ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String serialize(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object deserialize(String jsonText, TypeReference type) {
        try {
            return MAPPER.readValue(jsonText, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T deserialize(String jsonText, Class<T> beanClass) {
        try {
            return MAPPER.readValue(jsonText, beanClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonNode deserialize(String jsonText) {
        try {
            return MAPPER.readTree(jsonText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}