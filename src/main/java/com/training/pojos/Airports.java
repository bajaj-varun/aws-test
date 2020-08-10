package com.training.pojos;

import lombok.Data;
import scala.Serializable;

import java.lang.reflect.Field;
import java.util.Arrays;

@Data
public class Airports implements Serializable {
    private String iata;
    private String airport;
    private String city;
    private String state;
    private String country;
    private Double latitude;
    private Double longitude;

    public Airports(String[] arr) {
        Field[] fields = this.getClass().getDeclaredFields();
        for(int i=0; i< fields.length; i++){
            Field f = fields[i];
            Class<?> type = f.getType();
            String str = arr[i];
            try {
                if(type.equals(Double.class))
                    f.set(this,Double.parseDouble(str));
                else
                    f.set(this, str);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}

