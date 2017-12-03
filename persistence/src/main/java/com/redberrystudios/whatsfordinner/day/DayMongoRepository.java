package com.redberrystudios.whatsfordinner.day;

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

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

@Repository
public class DayMongoRepository extends MongoRepository<DayEntity, Long> {

  private static final String COLLECTION_NAME = "days";

  private MongoCollection<DayEntity> collection;

  private GroupMongoRepository groupMongoRepository;

  @Autowired
  public DayMongoRepository(MongoDatabase db, GroupMongoRepository groupMongoRepository) {
    super(db);
    collection = database.getCollection(COLLECTION_NAME, DayEntity.class);

    this.groupMongoRepository = groupMongoRepository;
  }

  public Long save(DayEntity day) {
    if (day == null) {
      throw new IllegalArgumentException("DayEntity to save is null!");
    }

    BsonValue upsertId = collection.replaceOne(eq("_id", day.getId()),
        day,
        new UpdateOptions().upsert(true))
        .getUpsertedId();

    if (upsertId != null) {
      return upsertId.asInt64().getValue();
    } else {
      return day.getId();
    }
  }

  public Long delete(DayEntity day) {
    if (day == null) {
      throw new IllegalArgumentException("DayEntity to delete is null!");
    }

    collection.deleteOne(eq("_id", day.getId()));
    return day.getId();
  }

  public DayEntity find(Long dayId) {
    if (dayId == null) {
      return null;
    }

    return collection.find(eq("_id", dayId)).first();
  }

  public DayEntity findByGroup(Long groupId, Long dayId) {
    if (groupId == null || dayId == null) {
      return null;
    }

    GroupEntity groupEntity = groupMongoRepository.find(groupId);

    if (groupEntity.getDays().contains(dayId)) {
      return collection.find(eq("_id", dayId)).first();
    } else {
      return null;
    }
  }

  public List<DayEntity> findAllByGroup(Long groupId) {
    if (groupId == null) {
      return null;
    }

    GroupEntity groupEntity = groupMongoRepository.find(groupId);

    return collection.find(in("_id", groupEntity.getDays()))
        .into(new ArrayList<>());
  }
}
