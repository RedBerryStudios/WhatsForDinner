package com.redberrystudios.whatsfordinner.member;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.redberrystudios.whatsfordinner.MongoRepository;

public class MemberMongoRepository extends MongoRepository {

  private static final String COLLECTION_NAME = "members";

  MongoCollection<MemberEntity> collection = database
      .getCollection(COLLECTION_NAME, MemberEntity.class);

  public MemberMongoRepository(MongoDatabase db) {
    super(db);
  }

  public void createMember(MemberEntity member) {
    collection.insertOne(member);
  }

  public void updateMember(MemberEntity member, String field, Object value) {
    collection.updateOne(eq("id", member.getId()), set(field, value));
  }

  public void deleteMember(MemberEntity member) {
    collection.deleteOne(eq("id", member.getId()));
  }

}
