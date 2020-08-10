package com.training.pojos;

import lombok.Data;
import org.bson.Document;
import scala.Serializable;

import java.lang.reflect.Field;
import java.util.Arrays;

@Data
public class Carriers implements Serializable {
    private String Code;
    private String Description;

    public Carriers(String[] arr) {
        Field[] fields = this.getClass().getDeclaredFields();
        for(int i=0; i< fields.length; i++){
            try {
                String str = null;
                if(arr.length > i && arr[i] != null)
                    str = arr[i];
                fields[i].set(this, str);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
