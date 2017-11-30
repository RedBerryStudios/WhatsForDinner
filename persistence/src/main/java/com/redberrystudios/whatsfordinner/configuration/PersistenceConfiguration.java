package com.redberrystudios.whatsfordinner.configuration;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import java.net.UnknownHostException;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfiguration {

  private static final String DB_NAME = "whatsfordinner";

  private CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
      fromProviders(PojoCodecProvider.builder().automatic(true).build()));

  @Bean
  public MongoClient mongoClient() throws UnknownHostException {
    MongoClient mongoClient = new MongoClient();
    return mongoClient;
  }

  @Bean
  public MongoDatabase db(MongoClient mongoClient) {
    MongoDatabase db = mongoClient.getDatabase(DB_NAME).withCodecRegistry(pojoCodecRegistry);
    return db;
  }

}
