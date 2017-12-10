package com.redberrystudios.whatsfordinner.checklist;

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
public class ChecklistMongoRepository extends MongoRepository<ChecklistEntity, Long> {

  private static final String COLLECTION_NAME = "checklists";

  private MongoCollection<ChecklistEntity> collection;

  private GroupMongoRepository groupMongoRepository;

  @Autowired
  public ChecklistMongoRepository(MongoDatabase db, GroupMongoRepository groupMongoRepository) {
    super(db);
    collection = database.getCollection(COLLECTION_NAME, ChecklistEntity.class);

    this.groupMongoRepository = groupMongoRepository;
  }

  public Long save(ChecklistEntity checklist) {
    if (checklist == null) {
      throw new IllegalArgumentException("ChecklistEntity to save is null!");
    }

    collection.replaceOne(eq("_id", checklist.getId()), checklist, new UpdateOptions().upsert(true));

    return checklist.getId();
  }

  public Long delete(ChecklistEntity checklist) {
    if (checklist == null) {
      throw new IllegalArgumentException("ChecklistEntity to delete is null!");
    }

    collection.deleteOne(eq("_id", checklist.getId()));
    return checklist.getId();
  }

  public ChecklistEntity find(Long checklistId) {
    if (checklistId == null) {
      return null;
    }

    return collection.find(eq("_id", checklistId)).first();
  }

  public ChecklistEntity findByGroup(Long groupId, Long checkListId) {
    if (groupId == null || checkListId == null) {
      return null;
    }

    GroupEntity groupEntity = groupMongoRepository.find(groupId);

    if (groupEntity.getChecklists().contains(checkListId)) {
      return collection.find(eq("_id", checkListId)).first();
    } else {
      return null;
    }
  }

  public List<ChecklistEntity> findAllByGroup(Long groupId) {
    if (groupId == null) {
      return new ArrayList<>();
    }

    GroupEntity groupEntity = groupMongoRepository.find(groupId);

    return collection.find(in("_id", groupEntity.getChecklists()))
        .into(new ArrayList<>());

  }
}
