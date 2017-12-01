package com.redberrystudios.whatsfordinner.checklist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChecklistService {

  private ChecklistMongoRepository checklistMongoRepository;

  @Autowired
  public ChecklistService(ChecklistMongoRepository checklistMongoRepository) {
    this.checklistMongoRepository = checklistMongoRepository;
  }

  public Checklist find(Long checklistId) {
    return persistenceToService(checklistMongoRepository.find(checklistId));
  }

  public Checklist findByGroup(Long groupId, Long checklistId) {
    return persistenceToService(checklistMongoRepository.findByGroup(groupId, checklistId));
  }

  public List<Checklist> findAllByGroup(Long groupId) {
    return checklistMongoRepository.findAllByGroup(groupId).stream()
        .map(this::persistenceToService)
        .collect(Collectors.toList());
  }

  public void delete(Checklist checklist) {
    checklistMongoRepository.delete(serviceToPersistence(checklist));
  }

  public void save(Checklist checklist) {
    checklistMongoRepository.save(serviceToPersistence(checklist));
  }

  private Checklist persistenceToService(ChecklistEntity checklistEntity) {
    Checklist checklist = new Checklist();

    checklist.setId(checklistEntity.getId());
    checklist.setName(checklistEntity.getName());

    List<ChecklistElement> elements = checklistEntity.getElements().stream()
        .map(entity -> new ChecklistElement(entity.getId(), entity.isComplete(), entity.getText()))
        .collect(Collectors.toList());
    checklist.setElements(elements);

    return checklist;
  }

  private ChecklistEntity serviceToPersistence(Checklist checklist) {
    ChecklistEntity checklistEntity = new ChecklistEntity();

    checklistEntity.setId(checklist.getId());
    checklistEntity.setName(checklist.getName());

    List<ChecklistElementEntity> elements = checklist.getElements().stream()
        .map(serviceModel -> new ChecklistElementEntity(serviceModel.getId(), serviceModel.getComplete(), serviceModel.getText()))
        .collect(Collectors.toList());
    checklistEntity.setElements(elements);

    return checklistEntity;
  }

}
