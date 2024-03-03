package com.sideproject.healMingle.boundContext.member.service;

import com.sideproject.healMingle.base.rsData.RsData;
import com.sideproject.healMingle.boundContext.genFile.service.GenFileService;
import com.sideproject.healMingle.boundContext.member.entity.Jop;
import com.sideproject.healMingle.boundContext.member.entity.Member;
import com.sideproject.healMingle.boundContext.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 메서드들이 트랜잭션 내에서 실행됨을 나타내며, 일반적으로 읽기전용 트랜잭션을 사용
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final GenFileService genFileService;

	@Transactional // 이 메서드에서 수행되는 데이터베이스 작업을 트랜잭션으로 관리
	public RsData<Member> join( String username, String password, String nickname, String email, MultipartFile profileImg, Jop jop ) {

		if(findByUsername ( username ).isPresent ()) // 제공된 사용자 이름으로 기존회원을 검색
			return RsData.of ( "F-1", "%s(은)는 사용중인 아이디 입니다" .formatted ( username ));
		// 존재할 시 에러메시지 구현

		Member member = Member // 빌더 형식으로 mebmer 객체 생성
				.builder()
				.username(username) // 사용자 이름 설정
				.password(passwordEncoder.encode(password)) // 비밀번호 암호화하여 설정
				.nickname(nickname) // 별명 설정
				.email ( email ) // 이메일 설정
				.jop ( jop ) // 직업 설정
				.build(); // member 객체를 빌드

		member = memberRepository.save(member); // 생성된 mebmer객체를 데이터베이스에 저장

		if (profileImg != null) {
			genFileService.save(member.getModelName(), member.getId(), "common", "profileImg", 0, profileImg);
			// 사용자가 파일을 업로드 안할 시 이 코드는 실행이 되지 않고
			// 업로드 할 시 save에 메서드가 실행
			// save(현재 저장되는 파일과 관련된 모데일 이름 member의 모델이름, 데이터베이스에 저장된 객체의 ID, 파일의 종류나 카테코리를 지정하는 문자열, 저장되는 파일의 구체적인 용도나 타입을 지정하는 문자열, 파일의 순서나 버번을 지정하는 숫자, 실제로 저장할 파일 객체)
		}

		return RsData.of ( "S-1", "회원가입이 완료되었습니다", member ); // 성공 응답과 함께 member 객체 반환
	}

	public Optional < Member> findByUsername( String username) {
		return memberRepository.findByUsername(username);
		// 사용자 이름을 검색하고 없는 경우 Optional.empty()를 반환
	}

	public Optional < Member> findById( long id) {
		return memberRepository.findById(id);
		// 사용자 아이디을 검색하고 없는 경우 Optional.empty()를 반환
	}


	public RsData checkUsernameDup(String username) { // 아이디 중복 체크 관련 성공과 실패 응답 메시지 반환
		// RsData에서 Member 객체를 반환하지 않는 이유는 여기서는 단순한 성공 실패 응답 메시지만 반환 하면 되기 때문이다
		if (findByUsername(username).isPresent()) return RsData.of("F-1", "%s(은)는 사용중인 아이디입니다.".formatted(username));

		return RsData.of("S-1", "%s(은)는 사용 가능한 아이디입니다.".formatted(username));
		
	}
}