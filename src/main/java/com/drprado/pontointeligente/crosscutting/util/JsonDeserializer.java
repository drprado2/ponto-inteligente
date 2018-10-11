package com.drprado.pontointeligente.crosscutting.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

public class JsonDeserializer {

    private static volatile ObjectMapper instance;

    private JsonDeserializer() {
    }

    public static synchronized ObjectMapper getInstance(){
        if(instance == null){
            instance = new ObjectMapper();
            instance.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            instance.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            instance.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            instance.registerModule(new Jdk8Module());
            instance.registerModule(new ParameterNamesModule());
            instance.registerModule(new JavaTimeModule());
        }

        return instance;
    }
}
