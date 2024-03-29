package com.sideproject.healMingle.boundContext.member.entity;

import com.sideproject.healMingle.base.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;


@Entity
@NoArgsConstructor ( access = PROTECTED )
@AllArgsConstructor ( access = PROTECTED )
@Getter
@Setter
@ToString ( callSuper = true )
@SuperBuilder
public class Member extends BaseEntity {

	@Column ( unique = true )
	private String username;
	@Setter
	private String password;
	@Setter
	private String nickname;
	@Setter
	private String email;
	@Setter
	@Enumerated ( jakarta.persistence.EnumType.STRING )
	//	열거형을 상수가 아닌 이름으로 sql에 나타내고 싶을 때 사용하면 됨
	private Jop jop;

	public boolean isAdmin ( ) {
		return "admin".equals ( username );
	}

	public List < ? extends GrantedAuthority > getGrantedAuthorities ( ) {
		List < GrantedAuthority > grantedAuthorities = new ArrayList <> ( );

		// 모든 멤버는 member 권한을 가진다.
		grantedAuthorities.add ( new SimpleGrantedAuthority ( "member" ) );

		// username이 admin인 회원은 추가로 admin 권한도 가진다.
		if ( isAdmin ( ) ) {
			grantedAuthorities.add ( new SimpleGrantedAuthority ( "admin" ) );
		}

		return grantedAuthorities;
	}
}