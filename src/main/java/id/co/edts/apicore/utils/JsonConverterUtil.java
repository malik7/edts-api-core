package id.co.edts.apicore.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonConverterUtil {
    /**
     *
     * @param list  of Object
     * @param clazz Class to be convert to
     * @param <T>   Type Of Class
     * @return Type Of class on @param clazz<T>
     */
    public static <T> List<T> fromlist(final List list, final Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<T> ret = new ArrayList<>();
        for (Object item : list) {
            String itemString = null;
            try {
                itemString = objectMapper.writeValueAsString(item);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            T dto = null;
            try {
                dto = objectMapper.readValue(itemString, clazz);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ret.add(dto);
        }
        return ret;
    }

    /**
     *
     * @param object Object NON STRING!!
     * @param clazz  Class to be convert to
     * @param <T>    Type Of Class
     * @return Type Of class on @param clazz<T>
     */
    public static <T> T fromObject(final Object object, final Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String itemString = null;
        try {
            itemString = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            return objectMapper.readValue(itemString, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
