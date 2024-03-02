package com.sideproject.healMingle.base.ut;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;



public class Ut {
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