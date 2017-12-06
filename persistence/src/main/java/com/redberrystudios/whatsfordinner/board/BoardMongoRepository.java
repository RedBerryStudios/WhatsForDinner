package com.redberrystudios.whatsfordinner.board;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.redberrystudios.whatsfordinner.MongoRepository;
import com.redberrystudios.whatsfordinner.day.DayMongoRepository;
import com.redberrystudios.whatsfordinner.group.GroupEntity;
import com.redberrystudios.whatsfordinner.group.GroupMongoRepository;
import org.bson.BsonValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

@Repository
public class BoardMongoRepository extends MongoRepository<BoardEntity, Long> {

  private static final String COLLECTION_NAME = "boards";

  private MongoCollection<BoardEntity> collection;

  private GroupMongoRepository groupMongoRepository;

  private DayMongoRepository dayMongoRepository;

  @Autowired
  public BoardMongoRepository(MongoDatabase db, GroupMongoRepository groupMongoRepository, DayMongoRepository dayMongoRepository) {
    super(db);
    collection = database.getCollection(COLLECTION_NAME, BoardEntity.class);

    this.groupMongoRepository = groupMongoRepository;
    this.dayMongoRepository = dayMongoRepository;
  }

  public Long save(BoardEntity board) {
    if (board == null) {
      throw new IllegalArgumentException("BoardEntity to save is null!");
    }

    BsonValue upsertId = collection.replaceOne(eq("_id", board.getId()),
        board,
        new UpdateOptions().upsert(true))
        .getUpsertedId();

    if (upsertId != null) {
      return upsertId.asInt64().getValue();
    } else {
      return board.getId();
    }
  }

  public Long delete(BoardEntity board) {
    if (board == null) {
      throw new IllegalArgumentException("BoardEntity to delete is null!");
    }

    collection.deleteOne(eq("_id", board.getId()));
    return board.getId();
  }

  public BoardEntity find(Long boardId) {
    if (boardId == null) {
      return null;
    }
    return collection.find(eq("_id", boardId)).first();
  }

  public BoardEntity findByGroup(Long groupId, Long boardId) {
    if (groupId == null || boardId == null) {
      return null;
    }
    GroupEntity groupEntity = groupMongoRepository.find(groupId);

    Boolean isValidBoardId = groupEntity.getDays()
        .stream()
        .anyMatch(dayId -> dayMongoRepository.find(dayId).getBoards().contains(boardId));

    if (isValidBoardId) {
      return collection.find(eq("_id", boardId)).first();
    } else {
      return null;
    }
  }

  public List<BoardEntity> findAllByGroup(Long groupId) {
    if (groupId == null) {
      return new ArrayList<>();
    }

    GroupEntity groupEntity = groupMongoRepository.find(groupId);

    List<Long> boardIds = groupEntity.getDays()
        .stream()
        .map(dayId -> dayMongoRepository.find(dayId).getBoards())
        .flatMap(Collection::stream)
        .collect(Collectors.toList());

    return collection.find(in("_id", boardIds)).into(new ArrayList<>());
  }
}
