package kr.ac.skuniv.artsharing.exception;

import kr.ac.skuniv.artsharing.domain.dto.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

// 에러코드 400번으로 response되면 exception발생

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private UrlPathHelper urlPathHelper;

    @ExceptionHandler(UserDefineException.class)
    public ResponseEntity handleUserDefineException(HttpServletRequest request, UserDefineException e){
        /*
            UserDefineException 이 발생하면 실행될 메소드
            에러에 대한 정보를 콘솔창에 띄우고
            에러 코드를 앞단으로 return
         */
        String requestUrl = urlPathHelper.getRequestUri(request);

        logger.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        logger.info("예외 발생 시간 : " + LocalDateTime.now());
        logger.info("요청 HTTP 메소드 : " + request.getMethod());
        logger.info("요청 URL : " + requestUrl);
        logger.info("클라이언트 : " + request.getRemoteHost());
        logger.info("원본 에러 메세지 : " + e.getOriginalErrorMessage());
        logger.info("사용자 정의 에러 메세지 : " + e.getMessage());
        logger.info("예외발생 메소드 : " + e.getErrorMethod());
        logger.info("Cause : " + e.getCause());
        logger.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

        return new ResponseEntity(ErrorDto.builder()
                                .originalErrorMessage(e.getOriginalErrorMessage())
                                .errorMessage(e.getMessage())
                                .requestUrl(requestUrl).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(HttpServletRequest request, Exception e){
        /*
            우리가 정의한 Exception이 아닌 다른 Exception이 발생하면 실행될 메소드
         */

        String requestUrl = urlPathHelper.getRequestUri(request);

        logger.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        logger.info("예외 발생 시간 : " + LocalDateTime.now());
        logger.info("요청 HTTP 메소드 : " + request.getMethod());
        logger.info("요청 URL : " + requestUrl);
        logger.info("클라이언트 : " + request.getRemoteHost());
        logger.info("에러 메세지 : " + e.getMessage());
        logger.info("Cause : " + e.getCause());
        logger.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

        return new ResponseEntity(ErrorDto.builder()
                .originalErrorMessage(e.toString())
                .errorMessage("예상치 못한 예외")
                .requestUrl(requestUrl).build(), HttpStatus.BAD_REQUEST);
    }


}
