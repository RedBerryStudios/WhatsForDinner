package com.redberrystudios.whatsfordinner.group;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.redberrystudios.whatsfordinner.MongoRepository;
import com.redberrystudios.whatsfordinner.member.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;

public class GroupMongoRepository extends MongoRepository {

  private static final String COLLECTION_NAME = "groups";

  private MongoCollection<GroupEntity> collection;

  @Autowired
  public GroupMongoRepository(MongoDatabase db) {
    super(db);
    collection = database.getCollection(COLLECTION_NAME, GroupEntity.class);
  }

  public void createGroup(GroupEntity group) {
    collection.insertOne(group);
  }

  public void updateGroup(GroupEntity group) {
    collection.replaceOne(eq("id", group.getId()), group);
  }

  public void deleteGroup(GroupEntity group) {
    collection.deleteOne(eq("id", group.getId()));
  }

  public GroupEntity findGroup(String groupId){
    return collection.find(eq("id",groupId)).first();
  }
}
