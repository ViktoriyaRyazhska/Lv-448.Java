package academy.softserve.museum.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Serializer  {

    private final static ObjectMapper mapper = new ObjectMapper();

    /**
     * Method converts given object to JSON string
     *
     * @param obj object to serialize
     * @return serialized JSON string
     */
    public static String toJsonString(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }
}
