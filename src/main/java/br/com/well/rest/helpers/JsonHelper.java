package br.com.well.rest.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;

public class JsonHelper<T> {

    private final Class<T> typeParameterClass;

    private final ObjectMapper mapper;

    public JsonHelper(Class<T> typeParameterClass) {
        super();
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        mapper.registerModule(new JavaTimeModule());
        this.typeParameterClass = typeParameterClass;
    }

    public String jsonToString(T object) throws JsonProcessingException {
        return mapper.writer().withDefaultPrettyPrinter().writeValueAsString(object);
    }

    public T stringToObject(String json) throws JsonProcessingException {
        return mapper.readValue(json, typeParameterClass);
    }

    public List<T> stringToList(String json) throws JsonProcessingException {
        List<T> list = mapper.readValue(json, new TypeReference<>() {}); // This is creating a list of LinkedHashMap...
        List<T> temp = new ArrayList<>();

        // ...so I needed to reconvert them individually to the correct T type.
        for (T item: list)
            temp.add(stringToObject(jsonToString(item)));

        return temp;
    }


}
