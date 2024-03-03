package com.sideproject.healMingle.base.security;

import com.sideproject.healMingle.base.ut.Ut;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.io.IOException;

public class CustomSimpleUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final Log logger = LogFactory.getLog ( this.getClass ( ) );
	// 로깅을 위한 Log 인스턴스
	// 클래스 내부에서 발생하는 이벤트를 로깅하기 위해서
	// 오류가 발생한 부분에 대해서 인지가 가능

	private RequestCache requestCache = new HttpSessionRequestCache ( );
	// 인증 프로세스 전에 사용자의 원래 요청을 캐시하는데 사용
	// 이를 통해 인증 성공 후 사용자를 그의 원래 목적지로 리다이렉션 가능


	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	                                    Authentication authentication) throws ServletException, IOException {
		// 사용자 인증이 성공했을 때 호출
		
		SavedRequest savedRequest = this.requestCache.getRequest(request, response);
		// 사용자의 원래 요청을 캐시에서 가져옴
		// 사용자가 인증 과정 전에 접근하려고 헀던 페이지를 나타냄
		
		clearAuthenticationAttributes(request);
		// 세션에서 인증 관련 속성을 제거
		// 보안상 필요한 작업으로, 잠재적인 오래된 인증 데이터가 남아있지 않도록 함

		String targetUrl = savedRequest != null ? savedRequest.getRedirectUrl() : getDefaultTargetUrl();
		// 사용자를 리다이렉션할 목적지 URL 결정
		// 사용자의 원래 요청이 존재한다면, 그 요청의 URL을 사용하고 그렇지 않다면 기본 목적지 URL 사용
		

		targetUrl = Ut.url.modifyQueryParam(targetUrl, "msg", Ut.url.encodeWithTtl("환영합니다."));
		// 목적지 URL에 msg 쿼리 파라미터를 추가하거나 수정함
		// 환영합니다 인코딩됨
		
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
		// 최종적으로 사용자를 결정된 목적지 URL로 리다이렉션함

	}
}
// 사용자 인증 후의 리다이렉션 로직을 더 세밀하게 제어하기 위해 사용
// 기본적으로 스프링 시큐리티는 인증 성공 후 정의된 기본 페이지로 사용자를 리다이렉션 하는데 사용자가 인증 전에 특정 페이지를 요청 했을 경우,
// 그 페이지로 리다이렉션 하는 것이 더 사용자 친화적일 수 있다


