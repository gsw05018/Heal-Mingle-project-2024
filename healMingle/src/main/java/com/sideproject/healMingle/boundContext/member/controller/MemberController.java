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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ( "/usr/member" )
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final Rq rq;

	@GetMapping("/join")
	public String showJoin() {
		return "usr/member/join";
	}

	@PostMapping("/join")
	public String join( @Valid JoinForm joinForm) {
		RsData<Member> joinRs = memberService.join(joinForm.getUsername(), joinForm.getPassword(), joinForm.getNickname(), joinForm.getEmail (), joinForm.getJop ());
		if (joinRs.isFail ()) {
			return rq.historyBack ( joinRs.getMsg () );
		}

		return rq.redirect ( "/",joinRs.getMsg ());
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
		@NotNull
		private Jop jop;
	}
}