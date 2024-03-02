package com.sideproject.healMingle.boundContext.genFile.entity;

import com.sideproject.healMingle.base.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@SuperBuilder // 상속받은 클래스의 필드를 포함하는 빌더 패턴을 구현
@ToString(callSuper = true)
@Table(indexes = {
		@Index ( name = "idx1", columnList = "relId, relTypeCode, typeCode, type2Code")
})
// JPA 엔티티에 대한 데이터베이스 테이블을 생성할 때 인덱스를 정의하는데 사용
// 엔티티 클래스에 정의 필들를 기반으로 데이터베이스 테이블에 인덱스를 추가할 수 있다
// name : 인덱스의 이름을 설정
// columList : 인덱스에 포함될 열의 이름을 쉼표로 구분하여 나열
public class GenFile extends BaseEntity {
	private String relTypeCode; // 타입 코드
	private long relId; // 식별자
	private String typeCode; // 파일 타입 코드(ex: img, png 등)
	private String type2Code; // 보조 파일 타입 코드
	private String fileExtTypeCode; // 파일 확장자 타입 코드
	private String fileExtType2Code; // 파일 확장자 보조 타입 코드
	private int fileSize; // 파일 크기
	private int fileNo; // 파일 번호
	private String fileExt; // 파일 확장자
	private String fileDir; // 파일이 저장된 디렉토리 경로
	private String originFileName; // 원본 파일명
}
