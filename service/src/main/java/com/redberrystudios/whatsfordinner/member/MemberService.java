package com.redberrystudios.whatsfordinner.member;

import com.redberrystudios.whatsfordinner.group.Group;
import com.redberrystudios.whatsfordinner.group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class MemberService {

  private MemberMongoRepository memberMongoRepository;

  private GroupService groupService;

  @Autowired
  public MemberService(MemberMongoRepository memberMongoRepository, GroupService groupService) {
    this.memberMongoRepository = memberMongoRepository;
    this.groupService = groupService;
  }

  public List<Member> findAllByGroup(Long groupId) {
    return memberMongoRepository.findAllByGroup(groupId)
        .stream()
        .map(this::persistenceToService)
        .collect(toList());
  }

  public Member findByGroup(Long groupId, Long memberId) {
    return persistenceToService(memberMongoRepository.findByGroup(groupId, memberId));
  }

  public Member find(Long memberId) {
    return persistenceToService(memberMongoRepository.find(memberId));
  }

  private Member persistenceToService(MemberEntity memberEntity) {
    Member result = new Member();

    result.setId(memberEntity.getId());
    result.setName(memberEntity.getName());
    result.setEmail(memberEntity.getEmail());
    result.setPictureLink(memberEntity.getPictureLink());

    return result;
  }

  private MemberEntity serviceToPersistence(Member member) {
    MemberEntity memberEntity = new MemberEntity();

    memberEntity.setId(member.getId());
    memberEntity.setName(member.getName());
    memberEntity.setEmail(member.getEmail());
    memberEntity.setPictureLink(member.getPictureLink());

    return memberEntity;
  }
}
