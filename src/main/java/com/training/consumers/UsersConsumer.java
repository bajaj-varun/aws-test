package com.training.consumers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.training.IKafkaConstants;
import com.training.pojos.Users;
import com.training.serde.UserSerDe;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class UsersConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "TG1");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, UserSerDe.class);

        Consumer<String, Users> consumer = new KafkaConsumer(props);
        consumer.subscribe(Collections.singleton(IKafkaConstants.USERS_TOPIC));
        final int minBatchSize = 200;
        List<Document> buffer = new ArrayList<>();
        MongoConnect mongoConnect = MongoConnect.getInstance();

        while (true) {
            consumer.poll(1000).forEach(record -> {
                buffer.add(mongoConnect.getUserDocument(record.value()));
                System.out.println(record.key() + ", Value =>" + record.value());

                if (buffer.size() >= minBatchSize) {
                    // Store data to DB
                    mongoConnect.insertIntoDB(buffer);
                    consumer.commitSync();
                    buffer.clear();
                }
            });
        }
    }
}

/**
 * TODO: JavaDoc
 */
class MongoConnect {

    static MongoConnect mongoConnect = null;
    static MongoDatabase db = null;

    private MongoConnect() {
    }

    public static MongoConnect getInstance() {
        if (mongoConnect == null) {
            mongoConnect = new MongoConnect();

            // Some homework
            db = mongoConnect.getMongoDB();
        }

        return mongoConnect;
    }

    private MongoDatabase getMongoDB() {
        MongoClient mongoClient = MongoClients.create(IKafkaConstants.MONGO_CONN_STRING);
        MongoDatabase db = mongoClient.getDatabase(IKafkaConstants.USERS_MONGO_DB);

        return db;
    }

    Document getUserDocument(Users u) {
        return new Document("name", u.getName())
                .append("favorite_color", u.getFavorite_color())
                .append("favorite_number", u.getFavorite_number());
    }

    void insertIntoDB(List<Document> buffer) {
        MongoCollection collection = db.getCollection(IKafkaConstants.USERS_MONGO_DB);
        collection.insertMany(buffer);
    }

}
