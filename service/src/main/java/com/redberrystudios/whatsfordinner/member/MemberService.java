package com.redberrystudios.whatsfordinner.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class MemberService {

  private MemberMongoRepository memberMongoRepository;

  @Autowired
  public MemberService(MemberMongoRepository memberMongoRepository) {
    this.memberMongoRepository = memberMongoRepository;
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

  public Member findByEmail(String email) {
    return persistenceToService(memberMongoRepository.findByEmail(email));
  }

  public Long delete(Member member) {
    return memberMongoRepository.delete(serviceToPersistence(member));
  }

  public Long save(Member member) {
    return memberMongoRepository.save(serviceToPersistence(member));
  }

  private Member persistenceToService(MemberEntity memberEntity) {
    if (memberEntity == null) {
      return null;
    }

    Member result = new Member();

    result.setId(memberEntity.getId());
    result.setName(memberEntity.getName());
    result.setEmail(memberEntity.getEmail());
    result.setPictureLink(memberEntity.getPictureLink());

    return result;
  }

  private MemberEntity serviceToPersistence(Member member) {
    if (member == null) {
      return null;
    }

    MemberEntity memberEntity = new MemberEntity();

    memberEntity.setId(member.getId());
    memberEntity.setName(member.getName());
    memberEntity.setEmail(member.getEmail());
    memberEntity.setPictureLink(member.getPictureLink());

    return memberEntity;
  }
}
