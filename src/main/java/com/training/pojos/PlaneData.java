package com.training.pojos;

import lombok.Data;
import org.apache.commons.lang.math.NumberUtils;
import scala.Serializable;

import java.lang.reflect.Field;
import java.util.Arrays;

@Data
public class PlaneData implements Serializable {
    private String tailnum;
    private String type;
    private String manufacturer;
    private String issue_date;
    private String model;
    private String status;
    private String aircraft_type;
    private String engine_type;
    private Integer year;

    public PlaneData(String[] arr) {
        Field[] fields = this.getClass().getDeclaredFields();
        for(int i=0; i< fields.length; i++){
            Field f = fields[i];
            Class<?> type = f.getType();
            String str = null;
            if(arr.length > i && arr[i] != null)
                str = arr[i];
            try {
                if(type.equals(Integer.class))
                    f.set(this,(str!=null && NumberUtils.isNumber(str))?Integer.parseInt(str):-999);
                else
                    f.set(this, str);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public String getTailnum() {
        return tailnum;
    }

    public String getType() {
        return type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getIssue_date() {
        return issue_date;
    }

    public String getModel() {
        return model;
    }

    public String getStatus() {
        return status;
    }

    public String getAircraft_type() {
        return aircraft_type;
    }

    public String getEngine_type() {
        return engine_type;
    }

    public Integer getYear() {
        return year;
    }
}
