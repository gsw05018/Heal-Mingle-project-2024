package com.sideproject.healMingle.base.security;

import com.sideproject.healMingle.base.ut.Ut;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

public class CustomSimpleUrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure( HttpServletRequest request, HttpServletResponse response, AuthenticationException exception ) throws IOException, ServletException {
	// 인증 실패시 리다이렉션할 기본 URL 지정
		// failMsg에 쿼리 파라미터에 인코딩된 메시지를 추가
		setDefaultFailureUrl("/usr/member/login?failMsg=" + Ut.url.encodeWithTtl("올바르지 않은 회원정보 입니다."));
		
		super.onAuthenticationFailure ( request, response, exception );
		// 부모 클래스 onAuthenticationFailure 메서드를 호출하여 실제로 인증 실패 처리를 수행
		// 설정된 기본 실패 URL로 리다이렉션함
		
	}
}
