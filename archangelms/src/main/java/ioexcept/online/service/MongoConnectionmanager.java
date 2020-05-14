package ioexcept.online.service;

import java.net.UnknownHostException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

public class MongoConnectionmanager {

	
	/**
	 * 
	 * Make a connection to MongDB
	 * There are four cookie-cutter methods:
	 * MongoClient mongoClient = new MongoClient();
	 * MongoClient mongoClient = new MongoClient( "localhost" );
	 * MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
	 * 
	 * MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
	 * MongoClient mongoClient = new MongoClient(connectionString);
	 * 
	 */
	private MongoClient mongoClient = null;
	
	public MongoConnectionmanager() throws Exception {
		mongoClient = new MongoClient();
	}
	public MongoConnectionmanager(String host) throws Exception {
		mongoClient = new MongoClient(host);
	}
	public MongoConnectionmanager(String host, int port) throws Exception {
		mongoClient = new MongoClient(host,port);
	}
	public MongoConnectionmanager(MongoClientURI uri) throws Exception {
		mongoClient = new MongoClient(uri);
	}

	public MongoDatabase getDatabase(String dbase) throws Exception{
	 return mongoClient.getDatabase("sku");
	}
	
	public void closeConnection() {
		try {
			if(mongoClient != null) mongoClient.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
	}

}
