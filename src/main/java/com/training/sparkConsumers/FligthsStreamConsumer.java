package com.training.sparkConsumers;

import com.google.common.collect.ImmutableList;
import com.training.IKafkaConstants;
import com.training.pojos.Airports;
import com.training.pojos.Carriers;
import com.training.pojos.FlightsData;
import com.training.pojos.PlaneData;
import com.training.pojos.generatedSources.FlightDataAvroSchema;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
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
import scala.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FligthsStreamConsumer implements Serializable {

    public static final SparkConf conf = new SparkConf().setAppName("FlightsDataSparkStream").setMaster(IKafkaConstants.MASTER);
    public static final JavaStreamingContext ssc = new JavaStreamingContext(conf, Duration.apply(5000));
    public static SparkSession spark = new SparkSession(ssc.sparkContext().sc());
    private List<Airports> listAirports;
    private List<Dataset<PlaneData>> lstPd;
    private JavaDStream<Carriers> rddCarriers;
    private JavaDStream<FlightDataAvroSchema> rddFlightData;

    public static void main(String[] args) {
        ssc.sparkContext().setLogLevel("ERROR");

        FligthsStreamConsumer consumer = new FligthsStreamConsumer();
        consumer.loadReferenceDataRdd();
        consumer.getFlightsData();
        //consumer.getFlightsAvroData();

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
        //kafkaParams.put("value.deserializer", KafkaAvroDeserializer.class);
        //kafkaParams.put("schema.registry.url", IKafkaConstants.SCHEMA_REGISTRY_URL);
        kafkaParams.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        kafkaParams.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        //kafkaParams.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        kafkaParams.put("group.id", "TG4");
        return kafkaParams;
    }

    private void getFlightsData() {
        String topic = IKafkaConstants.RAW_FLIGHT_TOPIC;
        JavaInputDStream<ConsumerRecord<String, String>> stream =
                KafkaUtils.createDirectStream(
                        ssc, LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.Subscribe(
                                ImmutableList.of(topic),
                                getProperties())
                );

        JavaDStream<FlightsData> lstFd = stream.map(cr -> new FlightsData(cr.value().split(",")));
        lstFd.foreachRDD(fdd -> {
            Dataset<FlightsData> fd = spark.createDataFrame(fdd, FlightsData.class).as(Encoders.bean(FlightsData.class));
            /*lstPd.forEach(dsPd -> {
                fd.join(dsPd, fd.col("TailNum")
                        .equalTo(dsPd.col("tailnum")))
                        .show(20);
            });*/
            fd.printSchema();
            fd.show(20);
            /*fd.write().partitionBy("year")
                    .mode(SaveMode.Append).parquet(IKafkaConstants.FLIGHTDATA_PATH);*/
            //fd.show(20);
        });
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
        System.out.println("getFlightsAvroData");
        JavaDStream<ConsumerRecord<String, FlightDataAvroSchema>> stream = getFlightDataStream();
        stream.foreachRDD(rdd -> {
            rdd.saveAsTextFile("");
            /*ds.write()
                    .format("parquet")
                    .mode(SaveMode.Append)
                    .save(IKafkaConstants.FLIGHTDATA_PATH);*/
        });

        /*List<FlightsData> lst = new ArrayList<>();
        lst.add(new FlightsData(cr.value()));
        Dataset ds = spark.createDataFrame(lst, FlightsData.class);*/
        /*pd->pd.getYear()..filter(yr->yr.getYear()!=-999)
                .filter(tn->tn.getTailnum().equals(tailNum))
                .m
                .(x-> System.out.println(x))*/

    }

    private void loadReferenceDataRdd() {
        /*OtherStreamConsumers otherConsumers = new OtherStreamConsumers();
        listAirports = otherConsumers.getAirportsData();
        //rddCarriers = otherConsumers.getCarriersData();
        lstPd = otherConsumers.getPlanesData();*/

        /*rddAirports.print(10);
        rddCarriers.print(10);*/
        /*dstreamPlaneData.filter(pd -> (pd.getYear() != -999)).print(10);*/
    }

    private void deNormalizeRdd() {

    }
}