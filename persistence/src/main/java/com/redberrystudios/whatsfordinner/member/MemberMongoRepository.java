package com.redberrystudios.whatsfordinner.member;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.redberrystudios.whatsfordinner.MongoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberMongoRepository extends MongoRepository {

  private static final String COLLECTION_NAME = "members";

  private MongoCollection<MemberEntity> collection;

  @Autowired
  public MemberMongoRepository(MongoDatabase db) {
    super(db);
    collection = database.getCollection(COLLECTION_NAME, MemberEntity.class);
  }

  public void createMember(MemberEntity member) {
    collection.insertOne(member);
  }

  public void updateMember(MemberEntity member) {
    collection.replaceOne(eq("id", member.getId()), member);
  }

  public void deleteMember(MemberEntity member) {
    collection.deleteOne(eq("id", member.getId()));
  }

  public MemberEntity findMember(Long memberId) {
    return collection.find(eq("id", memberId)).first();
  }

}
