package com.redberrystudios.whatsfordinner.group;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.redberrystudios.whatsfordinner.MongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GroupMongoRepository extends MongoRepository {

  private static final String COLLECTION_NAME = "groups";

  private MongoCollection<GroupEntity> collection;

  @Autowired
  public GroupMongoRepository(MongoDatabase db) {
    super(db);
    collection = database.getCollection(COLLECTION_NAME, GroupEntity.class);
  }

  public void save(GroupEntity group) {
    collection.replaceOne(eq("_id", group.getId()),
        group,
        new UpdateOptions().upsert(true));
  }

  public void delete(GroupEntity group) {
    collection.deleteOne(eq("_id", group.getId()));
  }

  public GroupEntity find(Long groupId) {
    return collection.find(eq("_id", groupId)).first();
  }

  public GroupEntity findByJoinToken(String joinToken) {
    return collection.find(eq("joinToken", joinToken)).first();
  }
}
