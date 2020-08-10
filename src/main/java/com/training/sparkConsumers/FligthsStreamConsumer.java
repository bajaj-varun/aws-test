package com.training.sparkConsumers;

import com.google.common.collect.ImmutableList;
import com.training.IKafkaConstants;
import com.training.pojos.Airports;
import com.training.pojos.Carriers;
import com.training.pojos.FlightsData;
import com.training.pojos.PlaneData;
import com.training.pojos.generatedSources.FlightDataAvroSchema;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.*;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FligthsStreamConsumer {

    public static final SparkConf conf = new SparkConf().setAppName("FlightsDataSparkStream").setMaster(IKafkaConstants.MASTER);
    public static final JavaStreamingContext ssc = new JavaStreamingContext(conf, Duration.apply(5000));
    public static SparkSession spark = new SparkSession(ssc.sparkContext().sc());
    private List<Airports> listAirports;
    private List<PlaneData> listPlaneData;
    private JavaDStream<Carriers> rddCarriers;
    private JavaDStream<FlightDataAvroSchema> rddFlightData;

    public static void main(String[] args) {
        ssc.sparkContext().setLogLevel("ERROR");

        FligthsStreamConsumer consumer = new FligthsStreamConsumer();
        consumer.loadReferenceDataRdd();
        consumer.getFlightsAvroData();

        ssc.start();
        try {
            ssc.awaitTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Object> getProperties() {
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", IKafkaConstants.BOOTSTRAP_SERVERS);
        kafkaParams.put("key.deserializer", IKafkaConstants.stringDeserializer);
        kafkaParams.put("value.deserializer", KafkaAvroDeserializer.class);
        kafkaParams.put("schema.registry.url", IKafkaConstants.SCHEMA_REGISTRY_URL);
        kafkaParams.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        kafkaParams.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        kafkaParams.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        kafkaParams.put("group.id", "TG4");
        return kafkaParams;
    }

    // TODO: For now hardcode partition and Offset, need to think somthing better (Read commited offset from dynamodb)N
    private JavaInputDStream getFlightDataStream() {
        String topic = IKafkaConstants.AVRO_FLIGHT_TOPIC;
        TopicPartition tp = new TopicPartition(topic, 0);
        Map<TopicPartition, Long> map = new HashMap();
        map.put(tp, 0L);
        JavaInputDStream<ConsumerRecord<String, FlightDataAvroSchema>> stream =
                KafkaUtils.createDirectStream(
                        ssc, LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.Subscribe(
                                ImmutableList.of(topic),
                                getProperties(), map)
                );
        return stream;
    }

    // TODO: Mix and match with KStream as well :)
    private void getFlightsAvroData() {
        JavaDStream<ConsumerRecord<String, FlightDataAvroSchema>> stream = getFlightDataStream();
        Dataset<Row> pd = spark.createDataFrame(listPlaneData, PlaneData.class);

        JavaDStream fld = stream.map(cr -> cr.value());
        //KStream<String, FlightDataAvroSchema> kStream = stream;
        //Dataset<Row> fl = spark.createDataset(fld, FlightDataAvroSchema.class);
        //fld.dstream().;
    }

    private void loadReferenceDataRdd() {
        OtherStreamConsumers otherConsumers = new OtherStreamConsumers();
        listAirports = otherConsumers.getAirportsData();
        rddCarriers = otherConsumers.getCarriersData();
        listPlaneData = otherConsumers.getPlanesData();

        /*rddAirports.print(10);
        rddCarriers.print(10);
        rddPlaneData.filter(pd -> (pd.getYear() != -999)).print(10);*/
    }

    private void deNormalizeRdd() {

    }
}

class Temp{
    FlightDataAvroSchema flightDataAvroSchema;
    PlaneData planeData;
}