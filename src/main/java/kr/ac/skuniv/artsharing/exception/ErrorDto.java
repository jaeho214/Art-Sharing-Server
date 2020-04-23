package kr.ac.skuniv.artsharing.exception;

import lombok.*;

import java.util.List;

@Getter @Setter
public class ErrorDto {
    private String code;
    private String message;
    private int status;
    private List<FieldError> errors;

    @Builder
    public ErrorDto(String code, String message, int status, List<FieldError> errors) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.errors = errors;
    }



    public static ErrorDto of(ErrorCodeType errorCodeType){
        return ErrorDto.builder()
                .code(errorCodeType.getCode())
                .message(errorCodeType.getMessage())
                .build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError{
        private String field;
        private String value;
        private String reason;

        @Builder
        public FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }
    }


}
