package com.zerobase.mission2.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    ID_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "ID가 이미 존재합니다."),
    USERNAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "USERNAME이 이미 존재합니다."),
    STORE_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "해당 가게가 이미 존재합니다."),
    ID_UNMATCHED(HttpStatus.BAD_REQUEST, "아이디가 일치하지 않습니다."),
    PASSWORD_UNMATCHED(HttpStatus.BAD_REQUEST, "패스워드가 일치하지 않습니다."),
    STORE_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 가게를 찾을 수 없습니다."),
    PARTNER_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 파트너를 찾을 수 없습니다."),
    RESERVATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 예약을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 유저를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
