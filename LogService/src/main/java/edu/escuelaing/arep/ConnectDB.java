package edu.escuelaing.arep;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import edu.escuelaing.arep.Message;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Date;

public class ConnectDB {
    MongoClientURI uri;
    MongoClient mongoClient;

    public ConnectDB() {
        uri = new MongoClientURI("mongodb+srv://conejihan:admin@cluster0.la8jn.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
        mongoClient = new MongoClient(uri);
    }

    public void insertMessage(Message message){
        mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("Database");
        MongoCollection<Document> collection =database.getCollection("Message");
        Document document=new Document();
        document.put("info",message.getInfo());
        document.put("date",message.getDate());
        collection.insertOne(document);
    }

    public ArrayList<Message> getMessages() {
        MongoDatabase database = mongoClient.getDatabase("Database");
        MongoCollection<Document> collection = database.getCollection("Message");
        FindIterable findIterable = collection.find();
        ArrayList<Document> documents = new ArrayList<Document>();
        ArrayList<Message> messages = new ArrayList<Message>();
        findIterable.into(documents);
        for (Document doc : documents) {
            if (doc.get("info") != null && doc.get("date") != null) {
                messages.add(new Message((String) doc.get("info"), (Date) doc.get("date")));
            }
        }
        return messages;
    }
}
