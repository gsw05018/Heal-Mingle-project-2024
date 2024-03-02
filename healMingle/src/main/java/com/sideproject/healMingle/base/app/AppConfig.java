package com.sideproject.healMingle.base.app;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	// 파일 저장 경로와 관련된 설정을 처리
	@Getter
	public static String genFileDirPath;

	@Value("${custom.genFile.dirPath}") // yml 에 지정한 경로에 있는 데이터를 사용
	public void setFileDirPath(String genFileDirPath) { // 프로퍼티 값을 주입받아 클래스 내부에서 사용할 수 있도록 하는 설정자 메서드 역할을 함
		// 정적 필드에 값을 설정하기 위해 메서드 자체를 정적 메서드로 선언하지 않고, 대신 메서드 내에서 정적 필드 값을 직정 할당중
		AppConfig.genFileDirPath = genFileDirPath;
	}
}
