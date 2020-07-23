package com.training.pojos;

import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
public class Users {
    private String name, favorite_color;
    private int favorite_number;

    public Users() {
    }

    public Users(String name, String favorite_color, int favorite_number) {
        this.name = name;
        this.favorite_color = favorite_color;
        this.favorite_number = favorite_number;
    }

    public static String getUserSchemaString() {
        return "{\n" +
                "    \"namespace\": \"com.training\",\n" +
                "    \"type\": \"record\",\n" +
                "    \"name\": \"User\",\n" +
                "    \"fields\": [\n" +
                "        {\"name\": \"name\", \"type\": \"string\"},\n" +
                "        {\"name\": \"favorite_number\",  \"type\": \"int\"},\n" +
                "        {\"name\": \"favorite_color\", \"type\": \"string\"}\n" +
                "    ]\n" +
                "}";
    }

    public static AvroSchema getUsersSchema(){
        AvroMapper mapper = new AvroMapper();
        AvroSchema schema = null;
        try {
            schema = mapper.schemaFrom(getUserSchemaString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return schema;
    }
}
