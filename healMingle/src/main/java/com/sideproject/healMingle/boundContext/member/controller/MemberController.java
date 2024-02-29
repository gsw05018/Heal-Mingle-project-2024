package com.sideproject.healMingle.boundContext.member.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ( "/usr/member" )
public class MemberController {
	@GetMapping ( "/join" )
	public String showJoin ( ) {
		return "usr/member/join";
	}

	@PostMapping ( "/join" )
	public String join ( @Valid JoinForm joinForm ) {
		return "redirect:/";
	}

	@AllArgsConstructor
	@Getter
	public static class JoinForm {
		@NotBlank
		@Size ( min = 4, max = 20 )
		private final String username;

		@NotBlank
		@Size ( min = 4, max = 20 )
		private final String password;

	}
}