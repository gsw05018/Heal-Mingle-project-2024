package com.sideproject.healMingle.base.security;

import com.sideproject.healMingle.boundContext.member.entity.Member;
import com.sideproject.healMingle.boundContext.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	// 주어진 username에 해당하는 사용자의 상세 정보를 불러옴

		Member member = memberRepository.findByUsername ( username ).orElseThrow ( () -> new UsernameNotFoundException ( "username(%s) not found".formatted ( username ) ) );
		// memberRepository에 찾는 username이 없을 시 예외 발생

		return new User ( member.getUsername (), member.getPassword (), member.getGrantedAuthorities () );
		// 사용자 정보를 불러온 후 스프링 시큐리티의 UserDetail 인터페이스를 구현하는 User 객체를 생성하여 반환
	}
}
