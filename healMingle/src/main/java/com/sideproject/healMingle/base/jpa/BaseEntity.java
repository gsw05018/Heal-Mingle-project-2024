package com.sideproject.healMingle.base.jpa;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@SuperBuilder // 빌더 패턴을 구현하는 코드를 자동으로 생성
@MappedSuperclass // JPA의 어노테이션으로 이 클래스가 다른 클래스의 상위 클래스임을 지정
@NoArgsConstructor (access = PROTECTED) // 인자가 없는 생성자 자동 생성, PROTECTED로 하는 이유는 상속 구조에서 안전하게 사용하기 위함
@EntityListeners ( AuditingEntityListener.class)
@ToString //
@EqualsAndHashCode
public class BaseEntity {
	@Id
	@GeneratedValue (strategy = IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	@CreatedDate
	private LocalDateTime createDate;
	@LastModifiedDate
	private LocalDateTime modifyDate;

	@Transient // 아래 필드가 DB 필드가 되는 것을 막는다.
	@Builder.Default
	private Map <String, Object> extra = new LinkedHashMap <> ();

	// 현재 객체의 클래스 이름을 가져와서 , 그 이름의 첫글자를 소문자로 변환한 후 나머지 이름을 그대로 붙어서 반환
	public String getModelName(){

		String simpleName = this.getClass ().getSimpleName ();
		// 현재 객체의 'class' 객체를 반환, class 객체로부터 단순한 이름을 문자열로 반환
		// ex) com.example.UserProfileImg 인 경우 UserProfileImg 을 반환한다
		return Character.toLowerCase ( simpleName.charAt ( 0 )) + simpleName.substring ( 1 ) ;
		// 첫 문자를 소문자로 반환한 후 클래스 이름의 첫 번째 문자를 제외한 나머지 문자열을 반환
		// ex) UserProfileImg > userProfileImg

	}
}