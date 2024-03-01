package com.sideproject.healMingle.boundContext.member.service;

import com.sideproject.healMingle.base.rsData.RsData;
import com.sideproject.healMingle.boundContext.member.entity.Jop;
import com.sideproject.healMingle.boundContext.member.entity.Member;
import com.sideproject.healMingle.boundContext.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public RsData<Member> join( String username, String password, String nickname, String email, Jop jop ) {
		Member member = Member
				.builder()
				.username(username)
				.password(passwordEncoder.encode(password))
				.nickname(nickname)
				.email ( email )
				.jop ( jop )
				.build();

		member = memberRepository.save(member);

		return RsData.of ( "S-1", "회원가입이 완료되었습니다", member );
	}

	public Optional < Member> findByUsername( String username) {
		return memberRepository.findByUsername(username);
	}

	public Optional < Member> findById( long id) {
		return memberRepository.findById(id);
	}


}