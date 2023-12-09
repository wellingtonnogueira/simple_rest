package br.com.well.rest.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonHelper<T> {

    private ObjectMapper mapper;

    public JsonHelper() {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    }

    public String jsonToString(T object) throws JsonProcessingException {
        return mapper.writer().withDefaultPrettyPrinter().writeValueAsString(object);
    }


}
