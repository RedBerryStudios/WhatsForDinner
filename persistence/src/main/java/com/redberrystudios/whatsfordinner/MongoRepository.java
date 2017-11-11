package com.redberrystudios.whatsfordinner;

import com.mongodb.client.MongoDatabase;

public abstract class MongoRepository {

  protected MongoDatabase database;

  public MongoRepository(MongoDatabase db) {
    this.database = db;
  }
}
