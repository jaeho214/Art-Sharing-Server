package kr.ac.skuniv.artsharing.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorDto {
    private String code;
    private String message;

    @Builder
    public ErrorDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorDto of(ErrorCodeType errorCodeType){
        return ErrorDto.builder()
                .code(errorCodeType.getCode())
                .message(errorCodeType.getMessage())
                .build();
    }
}
