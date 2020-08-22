package com.training.sparkConsumers;

import com.training.IKafkaConstants;
import com.training.pojos.Airports;
import com.training.pojos.Carriers;
import com.training.pojos.PlaneData;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OtherStreamConsumers {
    public static final SparkConf conf = new SparkConf().setAppName("OtherStreamApplication").setMaster(IKafkaConstants.MASTER);
    public static final JavaStreamingContext ssc = new JavaStreamingContext(conf, Duration.apply(5000));
    public static SparkSession spark = new SparkSession(ssc.sparkContext().sc());

    public static void main(String[] args) {
        ssc.sparkContext().setLogLevel("ERROR");

        OtherStreamConsumers consumer = new OtherStreamConsumers();
        consumer.getPlanesData();
        consumer.getAirportsData();

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
        kafkaParams.put("value.deserializer", IKafkaConstants.stringDeserializer);
        kafkaParams.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        kafkaParams.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        kafkaParams.put("group.id", "TG4");
        return kafkaParams;
    }

    private JavaInputDStream getDataStream(String topicName) {
        JavaInputDStream<ConsumerRecord<String, String>> stream =
                KafkaUtils.createDirectStream(
                        ssc, LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.Subscribe(
                                Collections.singleton(topicName), getProperties())
                );
        return stream;
    }

    public JavaDStream<Carriers> getCarriersData() {
        JavaInputDStream<ConsumerRecord<String, String>> stream = getDataStream(IKafkaConstants.CARRIER_TOPIC);
        JavaDStream<Carriers> rdd = stream.map(cr -> {
            String str = cr.value().replace("\"", "").trim();
            Carriers carriers = new Carriers(str.split(","));
            return carriers;
        });
        return rdd;
    }

    public void getPlanesData() {
        JavaInputDStream<ConsumerRecord<String, String>> stream = getDataStream(IKafkaConstants.PLANE_DATA_TOPIC);
        stream.foreachRDD(rdd -> {
            Dataset<PlaneData> pd = spark.createDataFrame(
                    rdd.map(x -> new PlaneData(x.value().split(","))), PlaneData.class)
                    .as(Encoders.bean(PlaneData.class));
            pd.show(20);
            pd.write()
                    .partitionBy("year", "model")
                    .mode(SaveMode.Append)
                    .parquet(IKafkaConstants.PLANE_DATA_PATH);
        });
    }

    public void getAirportsData() {
        JavaInputDStream<ConsumerRecord<String, String>> stream = getDataStream(IKafkaConstants.AIRPORTS_TOPIC);
        stream.foreachRDD(rdd -> {
            Dataset<Airports> ad = spark.createDataFrame(
                    rdd.map(x -> new Airports(x.value().split(","))), Airports.class)
                    .as(Encoders.bean(Airports.class));
            ad.show(20);
                    ad.write()
                    .partitionBy("country")
                    .mode(SaveMode.Append)
                    .parquet(IKafkaConstants.AIRPORTS_DATA_PATH);
        });
    }
}