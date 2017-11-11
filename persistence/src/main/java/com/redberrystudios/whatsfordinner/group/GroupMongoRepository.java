package com.redberrystudios.whatsfordinner.group;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.redberrystudios.whatsfordinner.MongoRepository;
import com.redberrystudios.whatsfordinner.member.MemberEntity;

public class GroupMongoRepository extends MongoRepository {

  private static final String COLLECTION_NAME = "groups";

  MongoCollection<GroupEntity> collection = database
      .getCollection(COLLECTION_NAME, GroupEntity.class);

  public GroupMongoRepository(MongoDatabase db) {
    super(db);
  }

  public void createGroup(GroupEntity group) {
    collection.insertOne(group);
  }

  public void updateGroup(GroupEntity group, String field, Object value) {
    collection.updateOne(eq("id", group.getId()), set(field, value));
  }

  public void deleteGroup(MemberEntity member) {
    collection.deleteOne(eq("id", member.getId()));
  }
}
