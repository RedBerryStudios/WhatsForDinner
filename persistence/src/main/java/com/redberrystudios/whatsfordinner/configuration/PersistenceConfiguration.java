package com.redberrystudios.whatsfordinner.configuration;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import java.net.UnknownHostException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfiguration {

  private static final String DB_NAME = "whatsfordinner";

  @Bean
  public MongoClient mongoClient() throws UnknownHostException {
    MongoClient mongoClient = new MongoClient();
    return mongoClient;
  }

  @Bean
  public MongoDatabase db(MongoClient mongoClient) {
    MongoDatabase db = mongoClient.getDatabase(DB_NAME);
    return db;
  }

}
