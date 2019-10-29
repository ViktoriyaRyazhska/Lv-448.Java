package inc.softserve.utils.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectToJson {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     *
     * @param object - any object
     * @return - json string
     */
    public static String map(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
