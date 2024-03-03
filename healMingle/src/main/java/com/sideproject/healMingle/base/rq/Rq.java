package com.sideproject.healMingle.base.rq;

import com.sideproject.healMingle.base.ut.Ut;
import com.sideproject.healMingle.boundContext.member.entity.Member;
import com.sideproject.healMingle.boundContext.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component 
@RequestScope
// 스프링 컴포넌트로 선언하여 스프링 빈으로 관리되게 하고, 요청 스코프로 지정
public class Rq {
	private final MemberService memberService;
	private final HttpServletRequest req; // HTTP 요청 정보
	private final HttpServletResponse resp; // HTTP 응답 정보
	private final HttpSession session;
	private final User user;
	private Member member = null;

	public Rq(MemberService memberService, HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
		// 생성자에서 의존성 주입과 함께 현재 인증 정보를 가져옴
		this.memberService = memberService;
		this.req = req;
		this.resp = resp;
		this.session = session;

		// 현재 로그인한 회원의 인증정보를 SecurityContextHolder 에서 가져옴
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication.getPrincipal() instanceof User) { // 인증 정보가 User 타입인 경우 해당 정보를 User 필드에 저장
			this.user = (User) authentication.getPrincipal();
		} else {
			this.user = null;
		}
	}

	// 로그인한 회원의 사용자명을 가져오는 메서드
	private String getLoginedMemberUsername() {
		if (isLogout()) return null;

		return user.getUsername(); // 로그인 상태인 경우 사용자명 반환
	}

	// 로그인 상태 메서드
	public boolean isLogin() {
		return user != null;
	}

	// 로그아웃 상태 메서드
	public boolean isLogout() {
		return !isLogin();
	}

	// 현재 로그인한 회원 정보를 가져오는 메서드
	public Member getMember() {
		if (isLogout()) {
			return null;
		}

		if (member == null) {
			member = memberService.findByUsername(getLoginedMemberUsername()).get();
		}

		return member;
	}

	// 현재 사용자가 관리자인지 확인하는 메서드
	public boolean isAdmin() {
		if (isLogout()) return false;

		return getMember().isAdmin();
	}

	// 세션 관련 함수
	// 세션에 속성을 설정하는 메서드
	public void setSession(String name, Object value) {
		session.setAttribute(name, value); 
	}

	// 세션에 속성을 설정하는 메서드, 기본값 설정 가능
	private Object getSession(String name, Object defaultValue) {
		Object value = session.getAttribute(name); // 세션에서 속성값 가져오기

		if (value == null) {
			return defaultValue;
		}

		return value;
	}

	// 세션에서 long 타입 속성값을 가져오는 메서드
	private long getSessionAsLong(String name, long defaultValue) {
		Object value = getSession(name, null); // 세션에서 속성값 가져오기

		if (value == null) return defaultValue;

		return (long) value;
	}

	// 세션에서 속성을 제거하는 메서드
	public void removeSession(String name) {
		session.removeAttribute(name);
	}

	// 쿠키 관련
	// 쿠키를 설정하는 메서드
	public void setCookie(String name, String value) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		resp.addCookie(cookie);
	}

	// 쿠키에서 값을 가져오는 메서드
	private String getCookie(String name, String defaultValue) {
		Cookie[] cookies = req.getCookies();

		if (cookies == null) {
			return defaultValue;
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}

		return defaultValue;
	}

	// 쿠키에서 long 타입 값을 가져오는 메서드
	private long getCookieAsLong(String name, int defaultValue) {
		String value = getCookie(name, null);

		if (value == null) {
			return defaultValue;
		}

		return Long.parseLong(value);
	}

	// 쿠키를 제거하는 메서드
	public void removeCookie(String name) {
		Cookie cookie = new Cookie(name, "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		resp.addCookie(cookie);
	}


	// 모든 쿠키 값을 문자열로 반환하는 메서드
	public String getAllCookieValuesAsString() {
		StringBuilder sb = new StringBuilder();

		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				sb.append(cookie.getName()).append(": ").append(cookie.getValue()).append("\n");
			}
		}

		return sb.toString();
	}

	// 세션에 저장 된 모든 값을 문자열로 반환하는 메서드
	public String getAllSessionValuesAsString() {
		StringBuilder sb = new StringBuilder();

		java.util.Enumeration<String> attributeNames = session.getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			String attributeName = attributeNames.nextElement();
			sb.append(attributeName).append(": ").append(session.getAttribute(attributeName)).append("\n");
		}

		return sb.toString();
	}

	// 이전 페이지로 돌아가는 자바 스크립트를 실행하는 메서드
	public String historyBack(String msg) {
		// referer 헤더에서 이전 페이지의 URL을 가져온다
		// 사용자가 현재 페이지로 오기 전에 마지막으로 방문한 페이지의 URL
		String referer = req.getHeader ( "referer" );
		// referer URL 기반으로 키를 생성한다
		// 로컬 스토리지에 저장될 오류 메시지의 키로 사용됨
		String key = "historyBackFailMsg___" + referer;
		// 생성한 키를 요청 속성으로 설정한다
		// 클라이언트 사이트 스크립트에서 로컬 스토리지에 접근할 때 사용됨
		req.setAttribute("localStorageKeyAboutHistoryBackFailMsg", key);
		// 오류 메시지를 요청 속성으로 설정
		// 클라이언트 사이드에서 사용자에게 보여줄 수 있다
		req.setAttribute ( "historyBackFailMsg", msg );
		// 응답 코드를  400으로 설정
		resp.setStatus ( HttpServletResponse.SC_BAD_REQUEST );

		return "common/js";
	}

	// 특정 URL로 리다이렉트하는 메서드
	public String redirect(String url, String msg) {
		return "redirect:" + Ut.url.modifyQueryParam ( url, "msg" , Ut.url.encode ( msg ));
		// 혹시나 ?msg가 있을 경우 ?msg를 덮어씌워서 오류가 나지 않게 수정함
		// String modifiedUrl = modifyQueryParam(url, "param2", "newValue2");
		// ex) url = "http://example.com/page?param1=value1&param2=value2";
		// deleteQueryParam을 호출하여 param2=value2를 URL에서 삭제후 URL http://example.com/page?param1=value1
		// addQueryParam을 호출하여 param2=newValue2를 URL에 추가후 최종 URL: http://example.com/page?param1=value1&param2=newValue2
	}
}