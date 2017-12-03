package com.redberrystudios.whatsfordinner.group;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.redberrystudios.whatsfordinner.MongoRepository;
import org.bson.BsonValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;

@Repository
public class GroupMongoRepository extends MongoRepository<GroupEntity, Long> {

  private static final String COLLECTION_NAME = "groups";

  private MongoCollection<GroupEntity> collection;

  @Autowired
  public GroupMongoRepository(MongoDatabase db) {
    super(db);
    collection = database.getCollection(COLLECTION_NAME, GroupEntity.class);
  }

  public Long save(GroupEntity group) {
    if (group == null) {
      throw new IllegalArgumentException("GroupEntity to save is null!");
    }

    BsonValue upsertId = collection.replaceOne(eq("_id", group.getId()),
        group,
        new UpdateOptions().upsert(true))
        .getUpsertedId();

    if (upsertId != null) {
      return upsertId.asInt64().getValue();
    } else {
      return group.getId();
    }
  }

  public Long delete(GroupEntity group) {
    if (group == null) {
      throw new IllegalArgumentException("GroupEntity to delete is null!");
    }

    collection.deleteOne(eq("_id", group.getId()));
    return group.getId();
  }

  public GroupEntity find(Long groupId) {
    if (groupId == null) {
      return null;
    }

    return collection.find(eq("_id", groupId)).first();
  }

  public GroupEntity findByJoinToken(String joinToken) {
    if (joinToken == null) {
      return null;
    }

    return collection.find(eq("joinToken", joinToken)).first();
  }

  public GroupEntity findByMember(Long memberId) {
    if (memberId == null) {
      return null;
    }

    return collection.find(eq("members", memberId)).first();
  }
}
