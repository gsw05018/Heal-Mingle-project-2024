package com.sideproject.healMingle.base.webMvc;

import com.sideproject.healMingle.base.app.AppConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
// spring 구성 클래스 선언
// 클래스 내에서 정의된 빈을 spring의 애플리케이션 컨텍스트에 등록
public class CustomWebMvcConfig implements WebMvcConfigurer {
	// CustomWebMvcConfig 클래스는 WebMvcConfigurer 인터페이스를 구현합니다.
	// 이를 통해 Spring MVC의 웹 구성을 사용자 정의할 수 있습니다.

	@Override
	public void addResourceHandlers( ResourceHandlerRegistry registry ){
		// WebMvcConfigurer 인터페이스의 addResourceHandlers 메서드를 오버라이드합니다.
		// 이 메서드는 애플리케이션의 정적 리소스를 처리하기 위한 리소스 핸들러를 등록하는 데 사용됩니다.

		registry.addResourceHandler ( "/gen/**" )
				// WebMvcConfigurer 인터페이스의 addResourceHandlers 메서드를 오버라이드합니다.
				// 이 메서드는 애플리케이션의 정적 리소스를 처리하기 위한 리소스 핸들러를 등록하는 데 사용됩니다.

				.addResourceLocations ( "file:///" + AppConfig.getGenFileDirPath () + "/" );
				// 실제 리소스가 위치하는 디렉토리를 지정합니다.
				// 여기서는 AppConfig.getGenFileDirPath() 메서드를 통해 얻은 경로를 사용합니다.
				// file:/// 프리픽스는 파일 시스템의 절대 경로를 나타냅니다.

	}
	// 등록된 리소스 핸들러는 '/gen/**' 패턴에 매칭되는 URL 요청이 들어왔을 때, 해당 요청을 처리하기 위한 정적 리소스 위치를 지정
	// 애플리케이션 외부에 위치한 파일에 접근할 수 있도록 한다
	// Spring MVC 애플리케이션에서는 클래스패스 내의 리소스나, 특정 내장 디렉토리 아래의 리소스에 대해서만 접근을 허용함
}
