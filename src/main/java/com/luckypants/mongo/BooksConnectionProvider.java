package com.luckypants.mongo;


import java.net.UnknownHostException;

import com.luckypants.properties.PropertiesLookup;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class BooksConnectionProvider {
	/**
	 * TODO:modify this method to allow passing the collection name to it
	 * @return
	 */

	public DBCollection getCollection() {
		try {
			PropertiesLookup pl = new PropertiesLookup();
			String mongoUrl = pl.getProperty("mongodbURL");
			String mongoDbPort = pl.getProperty("mongodbPORT");

			MongoClient mongo = new MongoClient(mongoUrl,Integer.parseInt(mongoDbPort));

			DB db = mongo.getDB("abhiDB");
			if (db == null) {
				System.out.println("Could not connect to Database");
			}

			boolean auth = db.authenticate("abhi", "abhi".toCharArray());
			if (auth == false) {
				System.out.println("Could not authenticate");
			}

			DBCollection booksColl = db.getCollection("books");
			return booksColl;

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void main(String[] args) {
		BooksConnectionProvider books = new BooksConnectionProvider();
		DBCollection booksCollection = books.getCollection();
		if(booksCollection == null){
			System.out.println("ERROR:No Connection");
		}
		else{
			System.out.println("SUCCESS:Connected");
		}

	}

}
