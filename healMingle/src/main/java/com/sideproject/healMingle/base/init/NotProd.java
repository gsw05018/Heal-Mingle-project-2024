package com.sideproject.healMingle.base.init;

import com.sideproject.healMingle.boundContext.member.service.MemberService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.stream.IntStream;

@Configuration
@Profile ( "!prod" )
public class NotProd {
	@Bean
	public ApplicationRunner init ( MemberService memberService ){
		return args -> {
			memberService.join ( "admin", "1234","admin","admin@test.com", null, null );

			IntStream.rangeClosed ( 1, 3 ).forEach ( i -> {
				memberService.join ( "user"  + i, "1234", "user" + i, "user" + "@test.com", null, null);
			} );
		};
	}
}
