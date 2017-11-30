package com.redberrystudios.whatsfordinner.member;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.redberrystudios.whatsfordinner.MongoRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberMongoRepository extends MongoRepository {

  private static final String COLLECTION_NAME = "members";

  private MongoCollection<MemberEntity> collection;

  @Autowired
  public MemberMongoRepository(MongoDatabase db) {
    super(db);
    collection = database.getCollection(COLLECTION_NAME, MemberEntity.class);
  }

  public void save(MemberEntity member) {
    collection.replaceOne(eq("_id", member.getId()),
        member,
        new UpdateOptions().upsert(true));
  }

  public void delete(MemberEntity member) {
    collection.deleteOne(eq("_id", member.getId()));
  }

  public MemberEntity find(Long memberId) {
    return collection.find(eq("_id", memberId)).first();
  }

  public MemberEntity findByGroup(Long groupId, Long memberId) {
    return collection.find(and(eq("groupId", groupId),
        eq("_id", memberId))).first();
  }

  public List<MemberEntity> findAllByGroup(Long groupId) {
    return collection.find(eq("groupId", groupId)).into(new ArrayList<>());
  }
}
