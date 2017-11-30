package com.redberrystudios.whatsfordinner;

import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class MongoRepository {

  protected MongoDatabase database;

  @Autowired
  public MongoRepository(MongoDatabase db) {
    this.database = db;
  }
}
