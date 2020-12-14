import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import org.bson.Document;

import java.util.Iterator;

import com.mongodb.MongoClient;

public class DatabaseOperations {

    public static MongoDatabase database;
    public static MongoClient mongo;
    public static String teams_collection = "teams";

    public static void connectToDatabase() {
        try {
            mongo = new MongoClient("localhost", 27017);
            database = mongo.getDatabase("simp");
            System.out.println("Connected to database");
        } catch (Exception e) {
            System.out.println("Not connected");
        }
    }

    public static void createCollection(String collection_name) {
        try {
            database.createCollection(collection_name);
        } catch (Exception e) {
            // do something
        }
    }

    public static MongoCollection<Document> getCollection(String collection_name) {
        createCollection(collection_name);
        return database.getCollection(collection_name);
    }

    public static FindIterable<Document> findAll(String collection_name) {
        createCollection(collection_name);
        return database.getCollection(collection_name).find();
    }

    public static void showAll(String collection_name) {
        FindIterable<Document> docs = findAll(collection_name);
        Iterator<Document> it = docs.iterator();
        while (it.hasNext()) {
            Document doc = it.next();
            System.out.println(doc);
        }
    }

    public static boolean addTeam(Document team) {
        try {
            createCollection(teams_collection);
            database.getCollection(teams_collection).insertOne(team);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Document getTeam(String id) {
        FindIterable<Document> documents;
        Document document = null;
        try {
            documents = database.getCollection("teams").find(Filters.eq("id", id));
            document = documents.first();
        } catch (Exception e) {
            // do something
        }
        return document;
    }

    public static void closeDatabase() {
        mongo.close();
    }

    public static void main(String args[]) {
        connectToDatabase();
        String[] players = { "player_2", "player_3", "player_4" };
        getCollection(teams_collection).updateOne(Filters.eq("name", "the starters"), Updates.push("players", players));
        showAll(teams_collection);
        closeDatabase();
    }
}