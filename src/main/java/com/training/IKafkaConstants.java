package com.training;

import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public interface IKafkaConstants {
    //String BOOTSTRAP_SERVERS="DESKTOP-2CGEI1G.localdomain:9092";
    String EC2_IP="<EC2>";
    String BOOTSTRAP_SERVERS=EC2_IP+":9092,"+EC2_IP+":9093";
    String SCHEMA_REGISTRY_URL="http://"+EC2_IP+":8081";

    String T0_TOPIC = "T0_Test";
    String USERS_TOPIC = "user";

    String RAW_FLIGHT_TOPIC = "raw_flight";
    String AVRO_FLIGHT_TOPIC= "avro_flight_new";
    String AVRO_2_FLIGHT_TOPIC= "avro_flight_2";
    String AIRPORTS_TOPIC   = "airports_topic";
    String CARRIER_TOPIC    = "carrier_topic";
    String PLANE_DATA_TOPIC = "plane_data_topic";
    String PLANE_DATA_AVRO_TOPIC = "plane_data_avro_topic";
    String myusertest_TOPIC = "myUserTest1";
    String ORDERS_TOPIC_NEW = "orders_topic";
    String AVRO_USER_TOPIC = "avro_users_topics";

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
    String MASTER="local[*]";


    // MongoDB related constants
    String USERS_MONGO_DB = "Users";
    String Flights_Data_MONGO_DB = "flights_data";

    // TODO: Creds list here not safe option revisit other options
    String MONGO_CONN_STRING = "mongodb+srv://<User>:<pwd>@<cluster>/TestDB)";

    // Hive related constants
    String NAMENODE_PATH_STR="hdfs://<NameNode>:8020/";
    String NAMENODE_PATH=NAMENODE_PATH_STR+"/user/varun/usersDB";
    String FLIGHTDATA_PATH=NAMENODE_PATH_STR+"/user/varun/FlightsDB";
    String PLANE_DATA_PATH=NAMENODE_PATH_STR+"/user/varun/PlanesDataDB";
    String AIRPORTS_DATA_PATH=NAMENODE_PATH_STR+"/user/varun/AirportsDB";
}
