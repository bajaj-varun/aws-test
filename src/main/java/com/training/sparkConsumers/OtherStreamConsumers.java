package com.training.sparkConsumers;

import com.training.IKafkaConstants;
import com.training.pojos.Airports;
import com.training.pojos.Carriers;
import com.training.pojos.PlaneData;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import java.util.*;

public class OtherStreamConsumers {
    private static final SparkConf conf = FligthsStreamConsumer.conf;
    private static final JavaStreamingContext ssc = FligthsStreamConsumer.ssc;
    private final SparkSession spark = FligthsStreamConsumer.spark;

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

    public List<PlaneData> getPlanesData() {
        JavaInputDStream<ConsumerRecord<String, String>> stream = getDataStream(IKafkaConstants.PLANE_DATA_TOPIC);
        List<PlaneData> lstPd = new ArrayList<>();
        JavaDStream<PlaneData> rdd = stream.map(cr -> {
            String str = cr.value().replace("\"", "").trim();
            PlaneData pd = new PlaneData(cr.value().split(","));
            lstPd.add(pd);
            return pd;
        });
        return lstPd;
    }

    public List<Airports> getAirportsData() {
        JavaInputDStream<ConsumerRecord<String, String>> stream = getDataStream(IKafkaConstants.AIRPORTS_TOPIC);
        List<Airports> lstAirports = new ArrayList<>();

        JavaDStream<Airports> rdd = stream.map(cr -> {
            String str = cr.value().replace("\"", "").trim();
            Airports ar = new Airports(str.split(","));
            lstAirports.add(ar);
            return ar;
        });
        return lstAirports;
    }
}

// TODO: as we write to disk, empty the list
/*flightsDataSet
    .write()
    .format("parquet")
    .mode(SaveMode.Append)
    .save(IKafkaConstants.NAMENODE_PATH);*/