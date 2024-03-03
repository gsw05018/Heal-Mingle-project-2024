package com.sideproject.healMingle.boundContext.member.controller;

import com.sideproject.healMingle.base.rq.Rq;
import com.sideproject.healMingle.base.rsData.RsData;
import com.sideproject.healMingle.boundContext.member.entity.Jop;
import com.sideproject.healMingle.boundContext.member.entity.Member;
import com.sideproject.healMingle.boundContext.member.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller // Spring MVC 컨트룰러로 선언
@RequestMapping ( "/usr/member" ) // 이 컨트룰러의 모든 핸들러 메서드에 대한 기본 URL 지정
@RequiredArgsConstructor // final이나 @NonNull 필드에 대한 생성자 자동 생성
public class MemberController {

	private final MemberService memberService;
	private final Rq rq;


	@PreAuthorize ( "isAnonymous()" )
	@GetMapping("/join") // "usr/member/join" Get 요청이 오면 실행
	public String showJoin() {
		return "usr/member/join";
	}
	// usr/member/join.html 반환

	@PreAuthorize ( "isAnonymous()" )
	@PostMapping("/join") // usr/member/join 으로 POST 요청이오면 실행
	public String join( @Valid JoinForm joinForm) { // 클라이언트로부터 전달받은 JoinForm 객체 검증
		RsData<Member> joinRs = memberService.join(joinForm.getUsername(), joinForm.getPassword(), joinForm.getNickname(), joinForm.getEmail (), joinForm.getProfileImg (),joinForm.getJop ());
		// MemberService를 통해 가입을 수행하고 결과를 받는다
		if (joinRs.isFail ()) { // 가입이 실패할 시 historyBack 실행
			return rq.historyBack ( joinRs.getMsg () ); // 실패 메시지 표현
		}

		return rq.redirect ( "/",joinRs.getMsg ());
		// 성공후 메인페이지로 이동후 성공 메시지 반환
	}

	@PreAuthorize ( "isAnonymous()" )
	@GetMapping("/login")
	public String showLogin(){
		return "usr/member/login";
	}

	// 아이디 중복 체크
	@GetMapping("/checkUsernameDup")
	@ResponseBody // 이 메시지의 반환 값은 응답 본문에 직접 작성
	public RsData checkUsernameDup(String username){ // 사용자 이름 중복 확인 요청을 처리
		return memberService.checkUsernameDup(username); // MemberService를 통해 사용자 이름의 중복 여부를 확인하고 결과를 반환
	}

	@InitBinder
	public void initBinder( WebDataBinder binder) {
//		WebDataBinder를 사용하여 요청으로부터 넘어오는 데이터를  Java  객체에 바인딩할 때 사용자는 설정을 커스터마이징한다
		binder.registerCustomEditor(Jop.class, new java.beans.PropertyEditorSupport () {
			@Override
			public void setAsText(String text) {
//				문자열 형태로 넘어온 값을 Jop 열거형으로 변환하는 로직을 정의
				// 예를 들어, "PHYSIOTHERAPIST" 문자열이 넘어오면 Jop.PHYSIOTHERAPIST 열거형 값으로 변환하여 설정합니다.
				setValue(Jop.valueOf(text.toUpperCase()));
			}
		});
	}

	@Getter
	@AllArgsConstructor
	public static class JoinForm {
		@NotBlank
		private String username;
		@NotBlank
		private String password;
		@NotBlank
		private String nickname;
		@NotBlank
		private String email;
		private MultipartFile profileImg;
		@NotNull
		private Jop jop; // 직업을 나타내는 열거형

	}
}