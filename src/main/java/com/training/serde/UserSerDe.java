package com.training.serde;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;
import com.training.pojos.Users;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

public class UserSerDe implements Serializer, Deserializer<Users> {
    private AvroSchema usersSchema;

    public UserSerDe() {
        usersSchema = Users.getUsersSchema();
    }

    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, Object data) {
        return null;
    }

    @Override
    public byte[] serialize(String topic, Headers headers, Object data) {
        AvroMapper mapper = new AvroMapper();
        byte[] b = null;
        try {
            b = mapper.writer(usersSchema).writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return b;
    }

    @Override
    public Users deserialize(String topic, byte[] data) {
        return null;
    }

    @Override
    public Users deserialize(String topic, Headers headers, byte[] data) {
        AvroMapper mapper = new AvroMapper();
        Users user = null;
        try {
            user = mapper.reader(Users.class)
                    .with(usersSchema)
                    .readValue(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void close() {

    }
}
