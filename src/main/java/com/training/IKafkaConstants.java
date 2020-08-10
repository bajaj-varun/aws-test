package com.training;

import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public interface IKafkaConstants {
    //String BOOTSTRAP_SERVERS="DESKTOP-2CGEI1G.localdomain:9092";
    String BOOTSTRAP_SERVERS="<EC2 Public IP>:9092";
    String SCHEMA_REGISTRY_URL="http://<EC2 Public IP>:8081";

    String T0_TOPIC = "T0";
    String USERS_TOPIC = "user";

    String RAW_FLIGHT_TOPIC = "raw_flight";
    String AVRO_FLIGHT_TOPIC= "avro_flight_new";
    String AVRO_2_FLIGHT_TOPIC= "avro_flight_2";
    String AIRPORTS_TOPIC   = "airports_topic";
    String CARRIER_TOPIC    = "carrier_topic";
    String PLANE_DATA_TOPIC = "plane_data_topic";

    Class<StringDeserializer> stringDeserializer = StringDeserializer.class;
    Class<StringSerializer> stringSerializer     = StringSerializer.class;

    Class<IntegerDeserializer> intDeserializer = IntegerDeserializer.class;
    Class<IntegerSerializer> intSerializer     = IntegerSerializer.class;

    // Something need to rethink
    String flightsDataCsv   = "C:\\FlightDataSamples\\StatsData\\FlightsData\\2008.csv";
    String airportsDataCsv  = "C:\\FlightDataSamples\\StatsData\\airports.csv";
    String carriersDataCsv  = "C:\\FlightDataSamples\\StatsData\\carriers.csv";
    String planeDataCsv     = "C:\\FlightDataSamples\\StatsData\\plane-data.csv";

    // Spark related settings
    String MASTER="local[3]";


    // MongoDB related constants
    String USERS_MONGO_DB = "Users";
    String Flights_Data_MONGO_DB = "flights_data";

    // TODO: Creds list here not safe option revisit other options
    String MONGO_CONN_STRING = "mongodb+srv://<USER>:<PWD>@<HOST>/"+USERS_MONGO_DB+"?retryWrites=true&w=majority";

    // Hive related constants
    String NAMENODE_PATH="hdfs://<EMR MASTER NODE>:8020//user/varun/usersDB";
}
