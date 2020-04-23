package kr.ac.skuniv.artsharing.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final UrlPathHelper urlPathHelper;

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity handleUserDefineException(HttpServletRequest request, BusinessLogicException e){
        /*
            UserDefineException 이 발생하면 실행될 메소드
            에러에 대한 정보를 콘솔창에 띄우고
            에러 코드를 앞단으로 return
         */
        String requestUrl = urlPathHelper.getRequestUri(request);
        ErrorCodeType errorType = e.getErrorCodeType();
        ErrorDto errorDto = ErrorDto.of(errorType);

        log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.info("예외 발생 시간 : " + LocalDateTime.now());
        log.info("요청 HTTP 메소드 : " + request.getMethod());
        log.info("요청 URL : " + requestUrl);
        log.info("클라이언트 : " + request.getRemoteHost());
        log.info("사용자 정의 에러 메세지 : " + e.getMessage());
        log.info("Cause : " + e.getCause());
        log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

        return new ResponseEntity(errorDto, HttpStatus.valueOf(errorType.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(HttpServletRequest request, Exception e){
        /*
            우리가 정의한 Exception이 아닌 다른 Exception이 발생하면 실행될 메소드
         */

        String requestUrl = urlPathHelper.getRequestUri(request);
        ErrorCodeType errorType = ErrorCodeType.UNKNOWN;
        ErrorDto errorDto = ErrorDto.of(errorType);

        log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        log.info("예외 발생 시간 : " + LocalDateTime.now());
        log.info("요청 HTTP 메소드 : " + request.getMethod());
        log.info("요청 URL : " + requestUrl);
        log.info("클라이언트 : " + request.getRemoteHost());
        log.info("에러 메세지 : " + e.getMessage());
        log.info("Cause : " + e.getCause());
        log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

        return new ResponseEntity(errorDto, HttpStatus.valueOf(errorType.getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        final BindingResult bindingResult = e.getBindingResult();
        final List<FieldError> errors = bindingResult.getFieldErrors();

        return new ResponseEntity(
                buildFieldErrors(ErrorCodeType.INPUT_VALUE_INVALID, toFieldError(errors)),
                HttpStatus.valueOf(String.valueOf(ErrorCodeType.INPUT_VALUE_INVALID)));
    }

    private ErrorDto buildFieldErrors(ErrorCodeType type, List<ErrorDto.FieldError> fieldErrors){
        return ErrorDto.builder()
                .code(type.getCode())
                .message(type.getMessage())
                .status(type.getStatus())
                .errors(fieldErrors)
                .build();
    }

    private List<ErrorDto.FieldError> toFieldError(List<FieldError> errors){
        return errors.parallelStream()
                .map(error -> ErrorDto.FieldError.builder()
                        .reason(error.getDefaultMessage())
                        .field(error.getField())
                        .value((String) error.getRejectedValue())
                        .build())
                .collect(Collectors.toList());
    }

}
