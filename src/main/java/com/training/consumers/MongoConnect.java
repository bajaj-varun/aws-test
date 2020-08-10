package com.training.consumers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.training.IKafkaConstants;
import com.training.pojos.Users;
import org.bson.Document;

import java.util.List;

/**
 * TODO: JavaDoc
 */
public class MongoConnect {

    static MongoConnect mongoConnect = null;
    static MongoDatabase db = null;

    private MongoConnect() {
    }

    public static MongoConnect getInstance() {
        if (mongoConnect == null) {
            mongoConnect = new MongoConnect();
        }

        return mongoConnect;
    }

    private MongoDatabase getMongoDB(String DBname) {
        MongoClient mongoClient = MongoClients.create(IKafkaConstants.MONGO_CONN_STRING);
        MongoDatabase db = mongoClient.getDatabase(DBname);

        return db;
    }

    Document getUserDocument(Users u) {
        return new Document("name", u.getName())
                .append("favorite_color", u.getFavorite_color())
                .append("favorite_number", u.getFavorite_number());
    }

    void insertIntoDB(List<Document> buffer, String dbName, String colName) {
        db = mongoConnect.getMongoDB(dbName);
        MongoCollection collection = db.getCollection(colName);
        collection.insertMany(buffer);
    }
}
