package com.nanlabs.images;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoStorageRepository implements StorageRepository{
	
	private MongoClient mongoClient;
	
	private String dbName;
	
	private static final String IMAGE_COLLECTION = "images";
	
	
	public MongoStorageRepository(MongoClient mongoClient, String dbName){
		this.mongoClient = mongoClient;
		this.dbName = dbName;
		//this.getImageCollection().ensureIndex(new BasicDBObject("resolutions.width", "2d"));
	}

	

	@Override
	public void store(Image image) {
		DBObject imageObject = BasicDBObjectBuilder.start().add("name", image.getName()).add("data", image.getData()).get();
		getImageCollection().insert(imageObject);
	}

	@Override
	public Image findBestFitting(String imageId, int preferredSize) {
		DBObject imageObject = getImageCollection().findOne(new BasicDBObject("name", imageId));
		if(imageObject != null){
			Image image = new Image(imageId, (byte[]) imageObject.get("data"), Image.DEFAULT_WIDTH);
			return image;			
		}else{
			return null;
		}
	}
	
	private DB getDB(){
		return mongoClient.getDB(dbName);
	}
	
	private DBCollection getImageCollection() {
		return this.getDB().getCollection(IMAGE_COLLECTION);
	}
	
	

}
