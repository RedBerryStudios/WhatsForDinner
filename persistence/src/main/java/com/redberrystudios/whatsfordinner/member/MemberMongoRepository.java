package com.redberrystudios.whatsfordinner.member;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.redberrystudios.whatsfordinner.MongoRepository;
import com.redberrystudios.whatsfordinner.group.GroupEntity;
import com.redberrystudios.whatsfordinner.group.GroupMongoRepository;
import org.bson.BsonValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

@Repository
public class MemberMongoRepository extends MongoRepository<MemberEntity, Long> {

  private static final String COLLECTION_NAME = "members";

  private MongoCollection<MemberEntity> collection;

  private GroupMongoRepository groupMongoRepository;

  @Autowired
  public MemberMongoRepository(MongoDatabase db, GroupMongoRepository groupMongoRepository) {
    super(db);
    collection = database.getCollection(COLLECTION_NAME, MemberEntity.class);

    this.groupMongoRepository = groupMongoRepository;
  }

  public Long save(MemberEntity member) {
    if (member == null) {
      throw new IllegalArgumentException("MemberEntity to save is null!");
    }

    if (member.getId() == null)
      member.setId(UUID.randomUUID().getLeastSignificantBits());

    BsonValue upsertId = collection.replaceOne(eq("_id", member.getId()),
        member,
        new UpdateOptions().upsert(true))
        .getUpsertedId();

    if (upsertId != null) {
      return upsertId.asInt64().getValue();
    } else {
      return member.getId();
    }
  }

  public Long delete(MemberEntity member) {
    if (member == null) {
      throw new IllegalArgumentException("MemberEntity to delete is null!");
    }

    collection.deleteOne(eq("_id", member.getId()));
    return member.getId();
  }

  public MemberEntity find(Long memberId) {
    if (memberId == null) {
      return null;
    }

    return collection.find(eq("_id", memberId)).first();
  }

  public MemberEntity findByGroup(Long groupId, Long memberId) {
    if (groupId == null || memberId == null) {
      return null;
    }

    GroupEntity groupEntity = groupMongoRepository.find(groupId);

    if (groupEntity.getMembers().contains(memberId)) {
      return collection.find(eq("_id", memberId)).first();
    } else {
      return null;
    }
  }

  public List<MemberEntity> findAllByGroup(Long groupId) {
    if (groupId == null) {
      return new ArrayList<>();
    }

    GroupEntity groupEntity = groupMongoRepository.find(groupId);
    return collection.find(in("_id", groupEntity.getMembers())).into(new ArrayList<>());
  }

  public MemberEntity findByEmail(String email) {
    if (email == null) {
      return null;
    }

    return collection.find(eq("email", email)).first();
  }
}
