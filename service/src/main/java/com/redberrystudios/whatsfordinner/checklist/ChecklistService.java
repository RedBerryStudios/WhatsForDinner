package com.redberrystudios.whatsfordinner.checklist;

import com.redberrystudios.whatsfordinner.board.BoardElement;
import com.redberrystudios.whatsfordinner.generator.IdentifierGeneratorService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChecklistService {

  private ChecklistMongoRepository checklistMongoRepository;

  private IdentifierGeneratorService identifierGeneratorService;

  @Autowired
  public ChecklistService(ChecklistMongoRepository checklistMongoRepository,
      IdentifierGeneratorService identifierGeneratorService) {
    this.checklistMongoRepository = checklistMongoRepository;
    this.identifierGeneratorService = identifierGeneratorService;
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

  public Long delete(Checklist checklist) {
    return checklistMongoRepository.delete(serviceToPersistence(checklist));
  }

  public Long save(Checklist checklist) {

    if (checklist.getId() == null) {

      Long id = identifierGeneratorService
          .generateLongIdentifier(i -> checklistMongoRepository.find(i) != null);

      checklist.setId(id);
    }

    for(ChecklistElement element : checklist.getElements()){
      if (element.getId() == null) {

        Long elementId = identifierGeneratorService
            .generateLongIdentifier(i -> {
              for(ChecklistElement e : checklist.getElements()) {
                if (e.getId() != null && e.getId().equals(i))
                  return true;
              }
              return false;
            });

        element.setId(elementId);
      }
    }

    return checklistMongoRepository.save(serviceToPersistence(checklist));
  }

  private Checklist persistenceToService(ChecklistEntity checklistEntity) {
    if (checklistEntity == null) {
      return null;
    }

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
    if (checklist == null) {
      return null;
    }

    ChecklistEntity checklistEntity = new ChecklistEntity();

    checklistEntity.setId(checklist.getId());
    checklistEntity.setName(checklist.getName());

    List<ChecklistElementEntity> elements = checklist.getElements().stream()
        .map(serviceModel -> new ChecklistElementEntity(serviceModel.getId(),
            serviceModel.getComplete(), serviceModel.getText()))
        .collect(Collectors.toList());
    checklistEntity.setElements(elements);

    return checklistEntity;
  }

}
