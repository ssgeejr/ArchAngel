package ioexcept.online.service;

//http://mongodb.github.io/mongo-java-driver/3.4/driver/getting-started/quick-start/

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class MongDBConnectionSample {

	private MongoConnectionmanager connMan = null;
	private  MongoDatabase mongodb = null;
	
	public MongDBConnectionSample() {
		try {
			openConnection();
			scrubData();
			loadData();
			fetchFirstRecord();
			fetchAllRecords();
			fetchFilteredRecords();
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			if (connMan != null) connMan.closeConnection();
		}
	}
	
	private void openConnection() throws Exception{
		// update the connection manager for you configuration
		System.out.println("==========>> Open Connection <<==========");
		connMan = new MongoConnectionmanager();
		mongodb = connMan.getDatabase("skupoc");
		System.out.println("__________>> END [Open Connection] <<__________");
	}
	
	private void scrubData() throws Exception{	
		System.out.println("==========>> Scrub Data <<==========");
		mongodb.getCollection("customers").drop();
		System.out.println("__________>> END [Scrub Data] <<__________");
		
	}
	
	private void loadData() throws Exception{
		System.out.println("==========>> Load Data <<==========");
		List<Document> documents = new ArrayList<Document>();
		documents.add(new Document("customerid", "7497A06A-BADA-2F1D-2939-B66693277EC4")
				.append("customer_name", "Quinn Craft")
				.append("company", "Turpis Nulla Foundation")
				.append("city", "Bozeman")
				.append("zipcode", "55063"));
		documents.add(new Document("customerid", "A6C0EDD5-1D66-787C-19AD-21EEC1BA0CA6")
				.append("customer_name", "Thane Howe")
				.append("company", "Montes Nascetur Ridiculus Corp.")
				.append("city", "Kearney")
				.append("zipcode", "63545"));
		documents.add(new Document("customerid", "80D434C6-73D9-E536-35F6-3AE8FA67867B")
				.append("customer_name", "Eric Patel")
				.append("company", "Vestibulum Mauris Magna Corporation")
				.append("city", "Olathe")
				.append("zipcode", "59710"));
		documents.add(new Document("customerid", "412F23E4-080C-455B-2F25-E7199638AF74")
				.append("customer_name", "Sonia Nielsen")
				.append("company", "Semper Limited")
				.append("city", "Laramie")
				.append("zipcode", "98080"));
		documents.add(new Document("customerid", "90BC96D0-BC4E-904B-34B2-F30827DEAADB")
				.append("customer_name", "Tarik Pruitt")
				.append("company", "Cursus A Corp.")
				.append("city", "Chandler")
				.append("zipcode", "86899"));
		
		MongoCollection<Document> collection = mongodb.getCollection("customers");
//		insert a single document
//		collection.insertOne(doc);
				
//		insert a List
		collection.insertMany(documents);
		System.out.println("Records inserted: " + collection.count());
		System.out.println("__________>> END [Load Data] <<__________");
		
	}
	
	private void fetchFirstRecord() throws Exception{
		System.out.println("==========>> Fetch First Record <<==========");
		MongoCollection<Document> collection = mongodb.getCollection("customers");
		Document firstcustomer = collection.find().first();
		System.out.println(firstcustomer.toJson());
		System.out.println("__________>> END [Fetch First Record] <<__________");
	}
	
	private void fetchAllRecords() throws Exception{
		System.out.println("==========>> Fetch All Records <<==========");
		MongoCollection<Document> collection = mongodb.getCollection("customers");
		MongoCursor<Document> cursor = collection.find().iterator();
	    while (cursor.hasNext()) {
	        System.out.println(cursor.next().toJson());
	    }
        System.out.println("__________>> END [Fetch All Records] <<__________");
	}
	
	private void fetchFilteredRecords() throws Exception{
		System.out.println("==========>> Fetch Filtered Record <<==========");
		MongoCollection<Document> collection = mongodb.getCollection("customers");
		Document olethaFilter = collection.find(eq("city", "Olathe")).first();
		System.out.println(olethaFilter.toJson());
		System.out.println("__________>> END [Fetch Filtered Record] <<__________");
	}

	public static void main(String[] args) {
		new MongDBConnectionSample();
	}

}
