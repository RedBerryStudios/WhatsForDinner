package com.redberrystudios.whatsfordinner.day;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.redberrystudios.whatsfordinner.MongoRepository;
import com.redberrystudios.whatsfordinner.group.GroupEntity;
import com.redberrystudios.whatsfordinner.group.GroupMongoRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DayMongoRepository extends MongoRepository {

  private static final String COLLECTION_NAME = "days";

  private MongoCollection<DayEntity> collection;

  private GroupMongoRepository groupMongoRepository;

  @Autowired
  public DayMongoRepository(MongoDatabase db, GroupMongoRepository groupMongoRepository) {
    super(db);
    collection = database.getCollection(COLLECTION_NAME, DayEntity.class);

    this.groupMongoRepository = groupMongoRepository;
  }

  public void save(DayEntity day) {
    collection.replaceOne(eq("_id", day.getId()),
        day,
        new UpdateOptions().upsert(true));
  }

  public void delete(DayEntity day) {
    collection.deleteOne(eq("_id", day.getId()));
  }

  public DayEntity find(Long dayId) {
    return collection.find(eq("_id", dayId)).first();
  }

  public DayEntity findByGroup(Long groupId, Long dayId) {
    GroupEntity groupEntity = groupMongoRepository.find(groupId);

    if (groupEntity.getDays().contains(dayId)) {
      return collection.find(eq("_id", dayId)).first();
    } else {
      return null;
    }
  }

  public List<DayEntity> findAllByGroup(Long groupId) {
    GroupEntity groupEntity = groupMongoRepository.find(groupId);

    return collection.find(in("_id", groupEntity.getDays()))
        .into(new ArrayList<>());
  }
}
