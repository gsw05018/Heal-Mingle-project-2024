package com.sideproject.healMingle.boundContext.member.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // 스프링부트 관련 컴포넌트 테스트할 때 붙여야 함, Ioc 컨테이너 작동시킴
@AutoConfigureMockMvc // http 요청, 응답 테스트
@Transactional // 실제로 테스트에서 발생한 DB 작업이 영구적으로 적용되지 않도록, test + 트랜잭션 => 자동롤백
@ActiveProfiles("test") // application-test.yml 을 활성화 시킨다.
public class MemberControllerTests {
	@Autowired
	private MockMvc mvc;

	@Test
	@DisplayName("회원가입 폼")
	void t001() throws Exception {
		// WHEN
		ResultActions resultActions = mvc
				.perform(get("/member/join")) // get을 요청해서 member/join 으로 들어간다
				.andDo(print()); // console에 테스트 과정 내용 출력

		// THEN
		resultActions
                .andExpect(handler().handlerType(MemberController.class))
				.andExpect(handler().methodName("showJoin"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().string(containsString("""
                        <input type="text" class="grow" placeholder="Username" name="username"
                        """.stripIndent().trim())))
				.andExpect(content().string(containsString("""
                       <input type="password" class="grow" placeholder="password" name="password"
                        """.stripIndent().trim())))
				.andExpect(content().string(containsString("""
                       <input type="password" class="grow" placeholder="password check" name="password_check"
                        """.stripIndent().trim())))
				.andExpect(content().string(containsString("""
                       <input type="email" class="grow" placeholder="email" name="email"
                        """.stripIndent().trim())))
				.andExpect(content().string(containsString("""
                       <input type="text" class="grow" placeholder="nickname" name="nickname"
                        """.stripIndent().trim())))
				.andExpect(content().string(containsString("""
                       <select class="select select-bordered w-full font-semibold" name="jopChoose"
                        """.stripIndent().trim())))
				.andExpect(content().string(containsString("""
                       <button type="submit" class="custom-btn btn-5 w-full"
                        """.stripIndent().trim())));
	}

	@Test
	@DisplayName("회원가입")
	void t002() throws Exception {
		// WHEN
		ResultActions resultActions = mvc
				.perform(post("/member/join")
						.with(csrf()) // CSRF 키 생성
						.param("username", "user10")
						.param("password", "1234")
				)
				.andDo(print());

		// THEN
		resultActions
				.andExpect(handler().handlerType(MemberController.class))
				.andExpect(handler().methodName("join"))
				.andExpect(status().is3xxRedirection());
	}
}