package com.training.serde;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;
import com.training.pojos.FlightsData;
import org.apache.avro.reflect.ReflectData;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

public class GenericSerializer<T> implements Serializer<T> {
    private Class<T> cls;
    private String avroSchemaStr;
    public static final String VALUE_SERIALIZER_CLASS = "value.serializer.class";
    public static final String VALUE_AVRO_SCHEMA_STRING = "value.avro.schema.string";

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        String clsName = String.valueOf(configs.get(VALUE_SERIALIZER_CLASS));
        avroSchemaStr  = String.valueOf(configs.get(VALUE_AVRO_SCHEMA_STRING));

        System.out.println(avroSchemaStr);
        //System.out.println(clsName);

        try {
            cls = (Class<T>) Class.forName(clsName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] serialize(String s, T cls) {
        return new byte[0];
    }

    @Override
    public byte[] serialize(String topic, Headers headers, T data) {
        AvroMapper mapper = new AvroMapper();
        byte[] b = null;
        try {
            AvroSchema schema = getSchema();
            b = mapper.writer(schema).writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return b;
    }

    @Override
    public void close() {

    }

    private AvroSchema getSchema(){
        AvroMapper mapper = new AvroMapper();
        AvroSchema schema = null;
        try {
            schema = mapper.schemaFrom(avroSchemaStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return schema;
    }
}
