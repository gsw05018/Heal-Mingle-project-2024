package com.sideproject.healMingle.boundContext.genFile.entity;

import com.sideproject.healMingle.base.app.AppConfig;
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

	public String getFileName(){
		return getId () + "." + getFileExt ();
	}
	// 파일의 고유 식별자와 파일 확장자를 결합하여 파일 이름을 생성
	// ex) ID 가 123, 확장자가 jpg면 getFileName : 123.jpg 반환

	public String getUrl(){
		return "/gen/" + getFileDir () + "/" + getFileName ();
	}
	// 파일이 웹에서 접근 가능한 URL 경로 생성
	// 파일이 저장된 디렉토리와 파일 이름을 사용하여 URL 구성
	// ex) 파일 디렉토리 : healMingle , 파일 이름 : 123.jpg > /gen/healMingle/123.jpg 반환

	public String getDownloadUrl(){
		return "/download/gen/" + getId ();
	}
	// 파일을 다운로드하기 위한 URL 생성
	// 파일의 고육 식별자를 사용하여 다운로드 URL 구성
	// ex) 파일 ID : 123 이면 /download/gen/123 반환

	public String getFilePath(){
		return AppConfig.getGenFileDirPath () + "/" + getFileDir () + "/" + getFileName ();
	}
	// 파일의 시스템에서 실제 경로 생성
	// 애플리케이션의 설정에서 정의된 기본 파일 저장 경로 + 파일이 저장된 디렉토리 + 파일이름을 사용하여 경로를 구성
	// 기본 파일 저장 경로가 c:/files, 파일 디렉토리가 images, 파일 이름이 123.jpg라면, 이 메서드는 c:/files/images/123.jpg를 반환
}
