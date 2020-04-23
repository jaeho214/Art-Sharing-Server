package kr.ac.skuniv.artsharing.exception;

import lombok.Getter;

@Getter
public enum  ErrorCodeType {

    //Common
    UNKNOWN(400, "COMMON_001", "UNKNOWN"),
    USER_UNAUTHORIZED(400, "COMMON_002", "이 기능을 사용할 수 없는 사용자입니다."),
    INPUT_VALUE_INVALID(400, "COMMON_003", "입력값이 올바르지 않습니다."),

    //art
    ART_NOT_FOUND(400, "ART_001", "해당 작품을 찾을 수 없습니다."),

    //artImage
    IMAGE_NOT_FOUND(400, "IMAGE_001", "해당 이미지를 찾을 수 없습니다."),
    IMAGE_CANNOT_NULL(400, "IMAGE_001", "하나의 이미지를 등록하셔야 합니다."),

    //member
    MEMBER_NOT_FOUND(400, "MEMBER_001", "해당 회원을 찾을 수 없습니다."),
    WRONG_PASSWORD(400, "MEMBER_002", "비밀번호가 틀렸습니다."),
    COOKIE_NOT_FOUND(400, "MEMBER_003", "로그인이 필요합니다."),


    //reply
    REPLY_NOT_FOUND(400,"REPLY_001", "해당 댓글을 찾을 수 없습니다."),

    //rent
    RENT_NOT_FOUND(400, "RENT_001", "해당 대여 기록을 찾을 수 없습니다."),
    ALREADY_RENT(400, "RENT_002", "이미 대여가 된 작품입니다.");

    private int status;
    private String code;
    private String message;

    ErrorCodeType(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
