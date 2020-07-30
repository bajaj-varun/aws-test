package com.training.sparkConsumers;

import com.training.IKafkaConstants;
import com.training.pojos.Users;
import com.training.serde.UserSerDe;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.hive.HiveContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserConsumer {

    public static void main(String[] args) {
        //new UserConsumer().getUserData();
        new UserConsumer().getUserData1();
    }

    private Map<String, Object> getProperties() {
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", IKafkaConstants.BOOTSTRAP_SERVERS);
        kafkaParams.put("key.deserializer", IKafkaConstants.stringDeserializer);
        kafkaParams.put("value.deserializer", UserSerDe.class);
        kafkaParams.put("group.id", "TG4");

        kafkaParams.put("auto.offset.reset", "earliest");
        kafkaParams.put("enable.auto.commit", false);
        return kafkaParams;
    }

    public void getUserData() {
        SparkSession spark = SparkSession
                .builder()
                .appName("test").master("local[*]")
                .getOrCreate();

        String jsonFormatSchema = Users.getUserSchemaString();

        Dataset<Row> df = spark
                .readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", IKafkaConstants.BOOTSTRAP_SERVERS)
                .option("subscribe", IKafkaConstants.USERS_TOPIC)
                .option("group.id", "TG0")
                .load();

        df.printSchema();
        //Dataset<Users> usrs = df.col("value").as(ExpressionEncoder.javaBean(Users.class));
    }

    public void getUserData1() {
        SparkConf conf = new SparkConf().setAppName("UserSparkStream").setMaster(IKafkaConstants.MASTER);
        JavaStreamingContext ssc = new JavaStreamingContext(conf, Duration.apply(1000));
        SparkSession spark = new SparkSession(ssc.sparkContext().sc());
       // HiveContext hc = new HiveContext(ssc.sparkContext());

        Collection<String> topics = Arrays.asList(IKafkaConstants.USERS_TOPIC);

        JavaInputDStream<ConsumerRecord<String, Users>> stream =
                KafkaUtils.createDirectStream(
                        ssc,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.Subscribe(topics, getProperties())
                );

        stream.foreachRDD(rdd0 -> {
            /*rdd0.foreach(row -> {
                Users users = row.value();
                System.out.println(users.toString());
            });*/

            Dataset df = spark.createDataFrame(rdd0.map(x->x.value()),Users.class);
            //df.printSchema();
            df.show(10);
        });


        ssc.start();
        try {
            ssc.awaitTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
