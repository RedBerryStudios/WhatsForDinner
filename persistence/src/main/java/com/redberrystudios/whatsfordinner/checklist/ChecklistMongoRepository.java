package com.redberrystudios.whatsfordinner.checklist;

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
public class ChecklistMongoRepository extends MongoRepository {

  private static final String COLLECTION_NAME = "checklists";

  private MongoCollection<ChecklistEntity> collection;

  private GroupMongoRepository groupMongoRepository;

  @Autowired
  public ChecklistMongoRepository(MongoDatabase db, GroupMongoRepository groupMongoRepository) {
    super(db);
    collection = database.getCollection(COLLECTION_NAME, ChecklistEntity.class);

    this.groupMongoRepository = groupMongoRepository;
  }

  public void save(ChecklistEntity checklist) {
    collection.replaceOne(eq("_id", checklist.getId()),
        checklist,
        new UpdateOptions().upsert(true));
  }

  public void delete(ChecklistEntity checklist) {
    collection.deleteOne(eq("_id", checklist.getId()));
  }

  public ChecklistEntity find(Long checklistId) {
    return collection.find(eq("_id", checklistId)).first();
  }

  public ChecklistEntity findByGroup(Long groupId, Long checkListId) {
    GroupEntity groupEntity = groupMongoRepository.find(groupId);

    if (groupEntity.getChecklists().contains(checkListId)) {
      return collection.find(eq("_id", checkListId)).first();
    } else {
      return null;
    }
  }

  public List<ChecklistEntity> findAllByGroup(Long groupId) {
    GroupEntity groupEntity = groupMongoRepository.find(groupId);

    return collection.find(in("_id", groupEntity.getChecklists()))
        .into(new ArrayList<>());

  }
}
