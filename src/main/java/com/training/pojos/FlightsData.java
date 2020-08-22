package com.training.pojos;

import com.fasterxml.jackson.dataformat.avro.AvroMapper;
import com.fasterxml.jackson.dataformat.avro.AvroSchema;
import com.training.pojos.generatedSources.FlightDataAvroSchema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.math.NumberUtils;
import org.bson.Document;
import org.spark_project.jetty.util.Fields;
import scala.Serializable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.stream.Collectors;

@Data
public class FlightsData implements Serializable {
    private Long Year;
    private Long Month;
    private Long DayofMonth;
    private Long DayOfWeek;
    private Long DepTime;
    private Long CRSDepTime;
    private Long ArrTime;
    private Long CRSArrTime;
    private String UniqueCarrier;
    private Long FlightNum;
    private String TailNum;
    private Long ActualElapsedTime;
    private Long CRSElapsedTime;
    private Long AirTime;
    private Long ArrDelay;
    private Long DepDelay;
    private String Origin;
    private String Dest;
    private Long Distance;
    private Long TaxiIn;
    private Long TaxiOut;
    private Long Cancelled;
    private String CancellationCode;
    private String Diverted;
    private String CarrierDelay;
    private String WeatherDelay;
    private String NASDelay;
    private String SecurityDelay;
    private String LateAircraftDelay;


    public FlightDataAvroSchema getFlightAvroData() {
        FlightDataAvroSchema data = new FlightDataAvroSchema();
        data.setYear(this.Year);
        data.setMonth(this.Year);
        data.setDayofMonth(this.DayofMonth);
        data.setDayOfWeek(this.DayOfWeek);
        data.setDepTime(this.DepTime);
        data.setCRSDepTime(this.CRSDepTime);
        data.setArrTime(this.ArrTime);
        data.setCRSArrTime(this.CRSArrTime);
        data.setUniqueCarrier(this.UniqueCarrier);
        data.setFlightNum(this.FlightNum);
        data.setTailNum(this.TailNum);
        data.setActualElapsedTime(this.ActualElapsedTime);
        data.setCRSElapsedTime(this.CRSElapsedTime);
        data.setAirTime(this.AirTime);
        data.setArrDelay(this.ArrDelay);
        data.setDepDelay(this.DepDelay);
        data.setOrigin(this.Origin);
        data.setDest(this.Dest);
        data.setDistance(this.Distance);
        data.setTaxiIn(this.TaxiIn);
        data.setTaxiOut(this.TaxiOut);

        data.setCancelled(this.Cancelled);
        data.setCancellationCode(this.CancellationCode);
        data.setDiverted(this.Diverted);
        data.setCarrierDelay(this.CarrierDelay);
        data.setWeatherDelay(this.WeatherDelay);
        data.setNASDelay(this.NASDelay);
        data.setSecurityDelay(this.SecurityDelay);
        data.setLateAircraftDelay(this.LateAircraftDelay);
        return data;
    }

    public FlightsData(){

    }

    public FlightsData(String[] arr) {
        Year                = NumberUtils.isNumber(arr[0])?Long.parseLong(arr[0]):-999;
        Month               = NumberUtils.isNumber(arr[1])?Long.parseLong(arr[1]):-999;
        DayofMonth          = NumberUtils.isNumber(arr[2])?Long.parseLong(arr[2]):-999;
        DayOfWeek           = NumberUtils.isNumber(arr[3])?Long.parseLong(arr[3]):-999;
        DepTime             = NumberUtils.isNumber(arr[4])?Long.parseLong(arr[4]):-999;
        CRSDepTime          = NumberUtils.isNumber(arr[5])?Long.parseLong(arr[5]):-999;
        ArrTime             = NumberUtils.isNumber(arr[6])?Long.parseLong(arr[6]):-999;
        CRSArrTime          = NumberUtils.isNumber(arr[7])?Long.parseLong(arr[7]):-999;
        UniqueCarrier       = arr[8];
        FlightNum           = NumberUtils.isNumber(arr[9])?Long.parseLong(arr[9]):-999;
        TailNum             = arr[10];
        ActualElapsedTime   = NumberUtils.isNumber(arr[11])?Long.parseLong(arr[11]):-999;
        CRSElapsedTime      = NumberUtils.isNumber(arr[12])?Long.parseLong(arr[12]):-999;
        AirTime             = NumberUtils.isNumber(arr[13])?Long.parseLong(arr[13]):-999;
        ArrDelay            = NumberUtils.isNumber(arr[14])?Long.parseLong(arr[14]):-999;
        DepDelay            = NumberUtils.isNumber(arr[15])?Long.parseLong(arr[15]):-999;
        Origin              = arr[16];
        Dest                = arr[17];
        Distance            = NumberUtils.isNumber(arr[18])?Long.parseLong(arr[18]):-999;
        TaxiIn              = NumberUtils.isNumber(arr[19])?Long.parseLong(arr[19]):-999;
        TaxiOut             = NumberUtils.isNumber(arr[20])?Long.parseLong(arr[20]):-999;
        Cancelled           = NumberUtils.isNumber(arr[21])?Long.parseLong(arr[21]):-999;
        CancellationCode    = arr[22];
        Diverted            = arr[23];
        CarrierDelay        = arr[24];
        WeatherDelay        = arr[25];
        this.NASDelay       = arr[26];
        SecurityDelay       = arr[27];
        LateAircraftDelay   = arr[28];
    }

    public FlightsData(FlightDataAvroSchema fl){
        Field[] fields = FlightsData.class.getDeclaredFields();
        for(Field f: fields){
            Class<?> type = f.getType();
            try {
                if(type.equals(Long.class))
                    f.set(this,fl.get(f.getName()));
                else {
                    f.set(this, fl.get(f.getName()).toString());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public Long getYear() {
        return Year;
    }

    public Long getMonth() {
        return Month;
    }

    public Long getDayofMonth() {
        return DayofMonth;
    }

    public Long getDayOfWeek() {
        return DayOfWeek;
    }

    public Long getDepTime() {
        return DepTime;
    }

    public Long getCRSDepTime() {
        return CRSDepTime;
    }

    public Long getArrTime() {
        return ArrTime;
    }

    public Long getCRSArrTime() {
        return CRSArrTime;
    }

    public String getUniqueCarrier() {
        return UniqueCarrier;
    }

    public Long getFlightNum() {
        return FlightNum;
    }

    public String getTailNum() {
        return TailNum;
    }

    public Long getActualElapsedTime() {
        return ActualElapsedTime;
    }

    public Long getCRSElapsedTime() {
        return CRSElapsedTime;
    }

    public Long getAirTime() {
        return AirTime;
    }

    public Long getArrDelay() {
        return ArrDelay;
    }

    public Long getDepDelay() {
        return DepDelay;
    }

    public String getOrigin() {
        return Origin;
    }

    public String getDest() {
        return Dest;
    }

    public Long getDistance() {
        return Distance;
    }

    public Long getTaxiIn() {
        return TaxiIn;
    }

    public Long getTaxiOut() {
        return TaxiOut;
    }

    public Long getCancelled() {
        return Cancelled;
    }

    public String getCancellationCode() {
        return CancellationCode;
    }

    public String getDiverted() {
        return Diverted;
    }

    public String getCarrierDelay() {
        return CarrierDelay;
    }

    public String getWeatherDelay() {
        return WeatherDelay;
    }

    public String getNASDelay() {
        return NASDelay;
    }

    public String getSecurityDelay() {
        return SecurityDelay;
    }

    public String getLateAircraftDelay() {
        return LateAircraftDelay;
    }

    public Document getFlightDataDocumentFromStr(String[] arr) {
        Field[] fields = FlightsData.class.getDeclaredFields();
        Document doc = new Document();
        for(int i=0; i< fields.length; i++){
            Field field = fields[i];
            String str = arr[i];
            doc.append(field.getName(), str);
        }
        return doc;
    }

    /*public static String getFlightsDataSchema() {
        String flightsDataStr = "";
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("src/main/resources/avro/flightsData.avsc"));
            flightsDataStr = fileReader.lines().collect(Collectors.joining());
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flightsDataStr;
    }*/
}

