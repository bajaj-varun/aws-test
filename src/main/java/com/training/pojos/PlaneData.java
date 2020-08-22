package com.training.pojos;

import com.training.pojos.generatedSources.PlanesDataAvroSchema;
import lombok.Data;
import org.apache.commons.lang.math.NumberUtils;
import scala.Serializable;

import java.lang.reflect.Field;

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

    public PlaneData(){

    }

    public PlaneData(String[] arr) {
        Field[] fields = this.getClass().getDeclaredFields();
        for(int i=0; i< fields.length; i++){
            Field f = fields[i];
            Class<?> type = f.getType();
            String str = null;
            str = (arr.length > i && arr[i] != null) ? arr[i] :"" ;
            try {
                if(type.equals(Integer.class) || type.equals(int.class))
                    f.set(this,(str!=null && NumberUtils.isNumber(str))?Integer.parseInt(str):-999);
                else
                    f.set(this, str);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public PlanesDataAvroSchema getPlaneDataAvroSchema(String[] arr){
        PlaneData pd = new PlaneData(arr);
        PlanesDataAvroSchema pda = new PlanesDataAvroSchema();
        pda.setAircraftType(pd.getAircraft_type());
        pda.setEngineType(pd.getEngine_type());
        pda.setIssueDate(pd.getIssue_date());
        pda.setManufacturer(pd.getManufacturer());
        pda.setModel(pd.getModel());
        pda.setStatus(pd.getStatus());
        pda.setTailnum(pd.getTailnum());
        pda.setType(pd.getType());
        pda.setYear(pd.getYear());
        return pda;
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

    @Override
    public String toString() {
        return tailnum +"," + type +","+ manufacturer +"," + issue_date + "," + model + ",'" + status + "," + aircraft_type + "," + engine_type + "," + year;
    }
}
