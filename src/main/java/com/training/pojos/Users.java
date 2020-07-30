package com.training.pojos;

import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Data
@Getter
@Setter
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFavorite_color() {
        return favorite_color;
    }

    public void setFavorite_color(String favorite_color) {
        this.favorite_color = favorite_color;
    }

    public int getFavorite_number() {
        return favorite_number;
    }

    public void setFavorite_number(int favorite_number) {
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

    public static AvroSchema getUsersSchema() {
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
