package com.redberrystudios.whatsfordinner.configuration;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import java.net.UnknownHostException;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfiguration {

  @Value("${server.mongodb}")
  private String mongoDbUri;

  private CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
      fromProviders(PojoCodecProvider.builder().automatic(true).build()));

  @Bean
  public MongoClientURI mongoClientUri() {
    return new MongoClientURI(mongoDbUri);
  }
  @Bean
  public MongoClient mongoClient(MongoClientURI mongoClientURI) throws UnknownHostException {
    MongoClient mongoClient = new MongoClient(mongoClientURI);
    return mongoClient;
  }

  @Bean
  public MongoDatabase db(MongoClient mongoClient, MongoClientURI mongoClientURI) {
    MongoDatabase db = mongoClient.getDatabase(mongoClientURI.getDatabase())
      .withCodecRegistry(pojoCodecRegistry);
    return db;
  }

}
