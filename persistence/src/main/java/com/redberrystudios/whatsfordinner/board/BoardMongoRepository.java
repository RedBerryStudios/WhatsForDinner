package com.redberrystudios.whatsfordinner.board;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.redberrystudios.whatsfordinner.MongoRepository;
import com.redberrystudios.whatsfordinner.day.DayMongoRepository;
import com.redberrystudios.whatsfordinner.group.GroupEntity;
import com.redberrystudios.whatsfordinner.group.GroupMongoRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardMongoRepository extends MongoRepository {

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

  public void save(BoardEntity board) {
    collection.replaceOne(eq("_id", board.getId()),
        board,
        new UpdateOptions().upsert(true));
  }

  public void delete(BoardEntity board) {
    collection.deleteOne(eq("_id", board.getId()));
  }

  public BoardEntity find(Long boardId) {
    return collection.find(eq("_id", boardId)).first();
  }

  public BoardEntity findByGroup(Long groupId, Long boardId) {
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
    GroupEntity groupEntity = groupMongoRepository.find(groupId);

    List<Long> boardIds = groupEntity.getDays()
        .stream()
        .map(dayId -> dayMongoRepository.find(dayId).getBoards())
        .flatMap(Collection::stream)
        .collect(Collectors.toList());

    return collection.find(in("_id", boardIds)).into(new ArrayList<>());
  }
}
