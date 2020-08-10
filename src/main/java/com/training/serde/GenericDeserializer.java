package com.training.serde;

import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;
import org.apache.avro.reflect.ReflectData;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class GenericDeserializer<T> implements Deserializer<T> {
    private Class<T> cls;
    public static final String VALUE_DESERIALIZER_CLASS = "value.deserializer.class";

    @Override
    public void configure(Map configs, boolean isKey) {
        String clsName = String.valueOf(configs.get(VALUE_DESERIALIZER_CLASS));
        try {
            cls = (Class<T>) Class.forName(clsName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T deserialize(String s, byte[] bytes) {
        return null;
    }

    @Override
    public T deserialize(String topic, Headers headers, byte[] data) {
        AvroMapper mapper = new AvroMapper();
        AvroSchema avroSchema = new AvroSchema(ReflectData.get().getSchema(cls));
        System.out.println(avroSchema.toString());
        Class obj = null;
        try {
            obj = mapper.reader(cls)
                    .with(avroSchema)
                    .readValue(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (T) obj;
    }

    @Override
    public void close() {

    }
}
