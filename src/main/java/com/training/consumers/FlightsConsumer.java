package com.training.consumers;

import com.training.IKafkaConstants;
import com.training.pojos.FlightsData;
import com.training.pojos.PlaneData;
import com.training.pojos.generatedSources.FlightDataAvroSchema;
import com.training.pojos.generatedSources.PlanesDataAvroSchema;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.bson.Document;

import java.sql.Time;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * Idea
 * |-> Validated unmodified flights data stream to DB (Mongo) -> Charts (Consumption rate, real time charts)
 * Stream Producers -> Broker -> Consumers -|-> Validated denormalized Stream join records store to HDFS -> Analytical charts, blend with historical data for timesearies or ML
 * *
 */
public class FlightsConsumer {

    public static void main(String[] args) {
        //new FlightsConsumer().consumeFlightsRawData();
        new FlightsConsumer().consumeAvroFlightData();
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "TG6");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IKafkaConstants.stringDeserializer);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        return props;
    }

    public void consumeFlightsRawData() {
        Properties props = getProperties();
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, IKafkaConstants.stringDeserializer);
        Consumer<String, String> consumer = new KafkaConsumer(props);
        consumer.subscribe(Collections.singleton(IKafkaConstants.RAW_FLIGHT_TOPIC));

        final int minBatchSize = 200;
        List<Document> buffer = new ArrayList<>();
        MongoConnect mongoConnect = MongoConnect.getInstance();
        // TODO: Add insertion timestamp so that we can query to assertain new records inserted
        while (true) {
            consumer.poll(2000).forEach(record -> {
                System.out.println(record.key() + "," + record.value());
                buffer.add(new FlightsData().getFlightDataDocumentFromStr(record.value().split(",")));
                // Store data to MongoDB
                if (buffer.size() >= minBatchSize) {
                    mongoConnect.insertIntoDB(buffer, IKafkaConstants.USERS_MONGO_DB, IKafkaConstants.Flights_Data_MONGO_DB);
                    consumer.commitSync();
                    buffer.clear();
                }

                consumer.commitAsync((offset, e) -> {
                    if (e == null)
                        System.out.println(offset.toString());
                });
            });
        }
    }

    public void consumeAvroFlightData() {
        Properties props = getProperties();
        props.put("application.id", "FlightDataStreamApplication");
        //props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
        props.put("schema.registry.url", IKafkaConstants.SCHEMA_REGISTRY_URL);
        //props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);

        Serde<FlightDataAvroSchema> flightSerde = new SpecificAvroSerde();
        flightSerde.configure(Collections.singletonMap("schema.registry.url", props.get("schema.registry.url")), false);
        Serde<PlanesDataAvroSchema> PDSerde = new SpecificAvroSerde();
        PDSerde.configure(Collections.singletonMap("schema.registry.url", props.get("schema.registry.url")), false);

        final PlaneDataKeyValueMapper kvMapper = new PlaneDataKeyValueMapper();
        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, FlightDataAvroSchema> fdT = builder
                .stream(IKafkaConstants.AVRO_FLIGHT_TOPIC,Consumed.with(Serdes.String(), flightSerde))
                .selectKey((k,v)->v.getTailNum().toString())
                .filter((k,v)->v.getTailNum()!=null);

        KStream<String, PlanesDataAvroSchema> pdT = builder.stream(IKafkaConstants.PLANE_DATA_AVRO_TOPIC,
                Consumed.with(Serdes.String(), PDSerde))
                .filter((k,v)->(v.getEngineType().toString()!="" || v.getEngineType().toString() != null));
                //.map((k,v)->KeyValue.pair(k,v.toString()));

        // Print on console
        // fdT.print(Printed.toSysOut());
        // pdT.print(Printed.toSysOut());
        final FlightAndPlaneDataJoiner joiner = new FlightAndPlaneDataJoiner();
        fdT.join(pdT,joiner, JoinWindows.of(Duration.ofSeconds(1)))
                .print(Printed.toSysOut());

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        //streams.cleanUp();
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}


class PlaneDataKeyValueMapper implements KeyValueMapper<String, String, KeyValue<String, PlaneData>>{
    @Override
    public KeyValue apply(String k, String v) {
        PlaneData pd = new PlaneData(v.split(","));
        return KeyValue.pair(pd.getTailnum(), pd);
    }
}

class FlightAndPlaneDataJoiner implements ValueJoiner<FlightDataAvroSchema, PlanesDataAvroSchema, KeyValue<String, String>> {
    @Override
    public KeyValue<String, String> apply(FlightDataAvroSchema ft, PlanesDataAvroSchema pd) {
        String ftTail = ft.getTailNum().toString();
        if (ftTail.equals(pd.getTailnum().toString())) {
            System.out.println("Test =>"+ftTail);
            FtAndPd dN = new FtAndPd();
            dN.setFt(ft);dN.setPd(pd);
            return KeyValue.pair(pd.getTailnum().toString(), dN.toString());
        }
        return null;
    }
}

class FtAndPd{
    private FlightsData ft;
    private PlanesDataAvroSchema pd;

    public FlightsData getFt() {
        return ft;
    }

    public void setFt(FlightDataAvroSchema fd) {
        this.ft = new FlightsData(fd);
    }

    public PlanesDataAvroSchema getPd() {
        return pd;
    }

    public void setPd(PlanesDataAvroSchema pd) {
        this.pd = pd;
    }

    @Override
    public String toString() {
        return "FtAndPd{" +
                "ft=" + ft.toString() +
                ", pd=" + pd.toString() +
                '}';
    }
}