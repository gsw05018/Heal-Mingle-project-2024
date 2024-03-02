package com.sideproject.healMingle.base.ut;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class Ut {

	public static class date{
		// 'date' 정적 내부 클래스느 날짜 관련 유틸리티 메서드를 제공

		public static String getCurrentDateFormatted(String pattern){
			// 주어진 패턴에 따라 현재 날짜를 포맷하여 문자열로 반환하는 메서드
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat ( pattern );
			// simpleDateFormat 객체 생성
			return simpleDateFormat.format ( new Date (  ) );
			// 현재 날짜를 주어진 패턴으로 포멧
		}

	}

	public static class file{
	// 파일 처리 관련 유틸리티 메서드

		public static String getExt(String filename) {
			// 주어진 파일 이름에서 확장자를 추출하여 반환하는 메서드
			return Optional.ofNullable ( filename ) // filename을 Optional 객체로 변환
					.filter ( f -> f.contains ( "." ) ) // 파일 이름에 "."이 포함되어 있는지 확인
					.map ( f -> f.substring ( filename.lastIndexOf ( "." ) + 1 ).toLowerCase () )
					// 마지막 '.' 이후의 문자열(확장자)를 추출하고 소문자로 변환합니다.
					.orElse ( "" );
					// 확장자를 찾을 수 없는 경우 빈 문자열을 반환합니다.
		}

		public static String getFileExtTypeCodeFromFileExt(String ext) {
			// 파일 확장자에 따라 파일 유형 코드를 반환하는 메서드

			switch ( ext ){
				case "jpeg":
				case "jpg":
				case "gif":
				case "png":
					return "img";   // 이미지 파일인 경우 "img"를 반환합니다.
				case "mp4":
				case "avi":
				case "mov":
					return "video"; // 비디오 파일인 경우 "video"를 반환합니다.
				case "mp3":
					return "audio"; // 오디오 파일인 경우 "audio"를 반환합니다
			}
			return "etc";   // 위의 경우에 해당하지 않는 다른 확장자인 경우 "etc"를 반환합니다
		}

		public static String getFileExtType2CodeFromFileExt(String ext){
			// 파일 확장자에 따라 또 다른 파일 유형 코드를 반환하는 메서드

			switch ( ext ){
				case "jpeg":
				case "jpg":
					return "jpg"; // "jpeg" 또는 "jpg" 확장자인 경우 "jpg"를 반환
				case "gif", "png", "mp4", "mov", "avi", "mp3":
					return ext; // 주어진 확장자를 그대로 반환
			}
			return "etc"; // 위의 경우에 해당하지 않는 다른 확장자인 경우 "etc"를 반환
		}

	}

	public static class url {
// encode 메서드는 주어진 문자열을  URL 에서 안전하게 사용할 수 있도록 인코딩 한다
		public static String encode(String message) { // 주어진 message을 URL로 인코딩하고, 인코딩된 문자열을 반환한다
			try {
				return URLEncoder.encode(message, "UTF-8");
				// `URLEncoder.encode` 호출하여 주어진 message를 UTF-8 인코딩을 사용하여 URL 인코딩한다
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		}
	}
}