package com.project.shop.global.exception.response;

import lombok.Getter;

@Getter
public class ErrorMessage {

    public static final String INVALID_INPUT_VALUE = "입력값이 올바르지 않습니다.";

    public static final String INVALID_PHONE_NUMBER = "전화번호 형식이 올바르지 않습니다.";
    public static final String INVALID_EMAIL = "이메일 형식이 올바르지 않습니다.";

    public static final String NOT_FOUND_USER = "해당 사용자를 찾을 수 없습니다.";

    public static final String INVALID_PASSWORD = "비밀번호 형식이 올바르지 않습니다.";
}
