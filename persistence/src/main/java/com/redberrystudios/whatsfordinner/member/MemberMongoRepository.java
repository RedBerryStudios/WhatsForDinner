package com.redberrystudios.whatsfordinner.member;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.redberrystudios.whatsfordinner.MongoRepository;
import com.redberrystudios.whatsfordinner.group.GroupEntity;
import com.redberrystudios.whatsfordinner.group.GroupMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

@Repository
public class MemberMongoRepository extends MongoRepository {

  private static final String COLLECTION_NAME = "members";

  private MongoCollection<MemberEntity> collection;

  private GroupMongoRepository groupMongoRepository;

  @Autowired
  public MemberMongoRepository(MongoDatabase db, GroupMongoRepository groupMongoRepository) {
    super(db);
    collection = database.getCollection(COLLECTION_NAME, MemberEntity.class);

    this.groupMongoRepository = groupMongoRepository;
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
    GroupEntity groupEntity = groupMongoRepository.find(groupId);

    if (groupEntity.getMembers().contains(memberId)) {
      return collection.find(eq("_id", memberId)).first();
    } else {
      return null;
    }
  }

  public List<MemberEntity> findAllByGroup(Long groupId) {
    GroupEntity groupEntity = groupMongoRepository.find(groupId);
    return collection.find(in("_id", groupEntity.getMembers())).into(new ArrayList<>());
  }
}
