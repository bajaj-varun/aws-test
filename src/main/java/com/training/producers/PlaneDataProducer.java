package com.training.producers;

import com.training.IKafkaConstants;
import com.training.pojos.PlaneData;
import com.training.pojos.generatedSources.PlanesDataAvroSchema;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class PlaneDataProducer {

    final public KafkaProducer _rawProducer;


    public PlaneDataProducer() {
        _rawProducer = new KafkaProducer(getProperties(), new StringSerializer(), new StringSerializer());
    }

    public static void main(String[] args) {
        //new PlaneDataProducer().planeDataProducer();
        new PlaneDataProducer().planeDataAvroProducer();
    }

    private Properties getProperties(){
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IKafkaConstants.stringSerializer);
        return props;
    }

    private List<String> getRawData(String csvPath) {
        List<String> flightsData = new ArrayList<>();
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(csvPath));
            flightsData = csvReader.lines()
                    .skip(1)
                    .collect(Collectors.toList());
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return flightsData;
    }

    void planeDataProducer() {
        for (String row : getRawData(IKafkaConstants.planeDataCsv)) {
            //System.out.println(row);
            ProducerRecord<String, String> pr = new ProducerRecord(IKafkaConstants.PLANE_DATA_TOPIC, "planeData", row);
            try {
                _rawProducer.send(pr, (recordMetadata, e) -> {
                    if(e==null)
                        System.out.println(IKafkaConstants.PLANE_DATA_TOPIC+" "+recordMetadata.toString());
                    else
                        e.printStackTrace();
                }).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    void planeDataAvroProducer() {
        Properties props = getProperties();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put("schema.registry.url", IKafkaConstants.SCHEMA_REGISTRY_URL);

        KafkaProducer avroProducer = new KafkaProducer(props);
        for (String row : getRawData(IKafkaConstants.planeDataCsv)) {
            PlanesDataAvroSchema pd = new PlaneData().getPlaneDataAvroSchema(row.split(","));
            //System.out.println(row);
            ProducerRecord<String, String> pr = new ProducerRecord(IKafkaConstants.PLANE_DATA_AVRO_TOPIC, pd.getTailnum().toString(), pd);
            try {
                avroProducer.send(pr, (recordMetadata, e) -> {
                    if(e==null)
                        System.out.println(IKafkaConstants.PLANE_DATA_AVRO_TOPIC+" "+recordMetadata.toString());
                    else
                        e.printStackTrace();
                }).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
