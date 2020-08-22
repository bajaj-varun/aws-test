package com.training.producers;

import com.training.IKafkaConstants;
import com.training.pojos.Airports;
import com.training.pojos.FlightsData;
import com.training.pojos.generatedSources.FlightDataAvroSchema;
import com.training.serde.GenericSerializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * TODO:
 * <p>
 * 1. Flights data not needed for long, think to delete after reading "commited offeset" value of this topic
 * 2. Airports, Carrier, Plane_data topic needed to retain data in topic
 */
public class FlightsProducer {

    final public KafkaProducer _rawProducer;
    Properties props = new Properties();

    public FlightsProducer() {
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IKafkaConstants.stringSerializer);
        _rawProducer = new KafkaProducer(props, new StringSerializer(), new StringSerializer());
    }

    public static void main(String[] args) {
        FlightsProducer flightsProducer = new FlightsProducer();
          //flightsProducer.rawFlightDataProducer();
          flightsProducer.avroFlightDataProducer();
          // other Producers
          //flightsProducer.airportsProducer();
           //flightsProducer.carriersProducer();

//        flightsProducer.avroFlightDataProducerTopic2();

        flightsProducer._rawProducer.flush();
        flightsProducer._rawProducer.close();
    }

    public List<String> getRawData(String csvPath) {
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

    /**
     * 1. Iterate over each line
     * 2. publish to Kafka topic
     * TODO: Key hardcode now but will think on it
     */
    public void rawFlightDataProducer() {
        for (String row : getRawData(IKafkaConstants.flightsDataCsv)) {
            System.out.println(row);
            ProducerRecord<String, String> pr = new ProducerRecord<>(IKafkaConstants.RAW_FLIGHT_TOPIC, "2008", row);
            _rawProducer.send(pr, (recordMetadata, e) -> {
                System.out.println(recordMetadata.toString());
            });
        }
    }

    /**
     * 1. Iterate over each line
     * 2. publish to Kafka topic
     * TODO: Key hardcode now but will think on it
     */
    public void avroFlightDataProducer() {
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put("schema.registry.url", IKafkaConstants.SCHEMA_REGISTRY_URL);

        KafkaProducer _avroProducer = new KafkaProducer(props);
        for (String row : getRawData(IKafkaConstants.flightsDataCsv)) {
            FlightDataAvroSchema data = new FlightsData(Pattern.compile(",").split(row)).getFlightAvroData();
            System.out.println(data.toString());
            ProducerRecord<String, FlightDataAvroSchema> pr = new ProducerRecord(IKafkaConstants.AVRO_FLIGHT_TOPIC, "2008", data);
            _avroProducer.send(pr, (recordMetadata, e) -> {
                if(e!=null)
                    e.printStackTrace();
                else
                    System.out.println(recordMetadata.toString());
            });
        }
    }

    public void avroFlightDataProducerTopic2() {
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GenericSerializer.class);
        props.put(GenericSerializer.VALUE_SERIALIZER_CLASS, FlightsData.class.getName());
        //props.put(GenericSerializer.VALUE_AVRO_SCHEMA_STRING, FlightsData.getFlightsDataSchema());

        KafkaProducer _avroProducer = new KafkaProducer(props);
        for (String row : getRawData(IKafkaConstants.flightsDataCsv)) {
            FlightsData data = new FlightsData(Pattern.compile(",").split(row));
            System.out.println(data.toString());
            ProducerRecord<String, FlightsData> pr = new ProducerRecord(IKafkaConstants.AVRO_2_FLIGHT_TOPIC, "2008", data);
            _avroProducer.send(pr, (recordMetadata, e) -> {
                if(e!=null)
                    e.printStackTrace();
                else
                    System.out.println(recordMetadata.toString());
            });
        }
    }

    void airportsProducer() {
        for (String row : getRawData(IKafkaConstants.airportsDataCsv)) {
            ProducerRecord<String, String> pr = new ProducerRecord(IKafkaConstants.AIRPORTS_TOPIC, "airports", row);
            _rawProducer.send(pr, (recordMetadata, e) -> {
                System.out.println(IKafkaConstants.AIRPORTS_TOPIC+" "+recordMetadata.toString());
            });
        }
    }

    void carriersProducer() {
        for (String row : getRawData(IKafkaConstants.carriersDataCsv)) {
            ProducerRecord<String, String> pr = new ProducerRecord(IKafkaConstants.CARRIER_TOPIC, "carriers", row);
            _rawProducer.send(pr, (recordMetadata, e) -> {
                System.out.println(IKafkaConstants.CARRIER_TOPIC+" "+recordMetadata.toString());
            });
        }
    }
}