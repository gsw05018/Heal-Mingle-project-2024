package com.sideproject.healMingle.boundContext.member.repository;

import com.sideproject.healMingle.boundContext.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
	// JpaRepository에서 기본적으로 제공하는 메서드
	// save() : 업데이트
	// findById() : 아이디 찾기
	// finalAll()  : 모든 엔티티의 리스트 반환
	// deleteById() // 주어진 아이디에 해당하는 엔티티 삭제
	// count() : 총 개수를 반환
	Optional<Member> findByUsername(String username);
}