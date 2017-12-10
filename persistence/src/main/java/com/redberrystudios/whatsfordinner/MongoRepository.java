package com.redberrystudios.whatsfordinner;

import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class MongoRepository<T, I> {

  protected MongoDatabase database;

  @Autowired
  public MongoRepository(MongoDatabase db) {
    this.database = db;
  }

  public abstract T find(I id);

  public abstract Long delete(T entity);

  public abstract Long save(T entity);
}
