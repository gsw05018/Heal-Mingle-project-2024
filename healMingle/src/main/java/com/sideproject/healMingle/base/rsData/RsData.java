package com.sideproject.healMingle.base.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RsData<T> { // 제네릭 타입 T를 사용하는 RsData 선언
	private String resultCode;
	private String msg;
	private T data;

	public static <T> RsData<T> of(String resultCode, String msg, T data) {
		// resultCode, msg, data를 매개변수로 받아 RsData 객체를 생성하고 반환하는 정적 팩토리 메서드
		return new RsData<>(resultCode, msg, data);
		// RsData 객체를 생성하고 반환
	}

	public static <T> RsData<T> of(String resultCode, String msg) {
		 // resultCode, msg를 매개변수로 받아 data 필드를 null로 가지는 RsData 객체를 생성하고 반환하는 정적 팩토리 메서드
		return of(resultCode, msg, null);
		// 위에 정의된 of 메서드를 재사용하여 RsData 객체를 생성하여 반환
	}

	public boolean isSuccess() {
		return resultCode.startsWith("S-");
		// S- 로 시작하면 성공을 반환하는 메서드
	}

	public boolean isFail() {
		return !isSuccess();
		// S-로 그 외로 시작하면 실패를 반환하는 메서드
	}
}