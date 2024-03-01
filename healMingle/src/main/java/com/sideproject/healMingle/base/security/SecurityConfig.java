package com.sideproject.healMingle.base.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
//spring 구성 클래스임을 알려줌
@EnableWebSecurity
// spring security 활성화 및 보안구성 제공함
@EnableMethodSecurity
public class SecurityConfig{

	@Bean
		// Spring Bean으로 SecurityFilterChain 객체를 등록
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf((csrf) -> csrf // CSRF 보호 설정
						.ignoringRequestMatchers(new AntPathRequestMatcher("/mysql-console/**")))
				// 특정 경로에 대한 CSRF 보호 비활성화

				.headers((headers) -> headers // HTTP 헤더 설정을 위한 코드
						.addHeaderWriter(new XFrameOptionsHeaderWriter( // 클릭재킹 보호를 위해 XFrameOptions 헤더 설정
								XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))

				.formLogin((formLogin) -> formLogin // 폼 로그인 구성을 위한 설정
						.loginPage("/usr/member/login") // 사용자 정의 로그인 페이지 URL 설정
				)
				.logout((logout) -> logout // 로그아웃 구성을 위한 설정
						.logoutRequestMatcher(new AntPathRequestMatcher("/usr/member/logout")) // 로그아웃 URL 설정
						.logoutSuccessUrl("/") // 로그아웃 성공 시 redirect URL 설정
						.invalidateHttpSession(true)) // 로그아웃 시 session 무효화
		;
		return http.build(); // 설정된 HttpSecurity 객체를 빌드하여 반환
	}
	// SecurityFilterChain : HTTP 요청에 대한 보안 필터 체인 정의, 여기서는 CSRF 보호, HTTP 헤더 설정, 인증 규칙 , 로그인 및 로그아웃 처리 설정
	// HttpSecurity : HTTP 보안 설정을 위한 객체, 웹 기반 보안을 구성

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	// PasswordEncoder 빈을 등록, 여기서는 BCrypt 알고리즘 사용
	// 사용자 비밀번호를 안전하게 저장하기 위한 인코더
	// BCryptPasswordEncoder을 이용하여 비밀번호 암호화

}


