package com.fastcampus.programming.dmaker.exception;

import com.fastcampus.programming.dmaker.dto.DMakerErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice // 각 컨트롤러에게 조언을 해준다 ->
public class DMakerExceptionHandler {
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(DMakerException.class)
    public DMakerErrorResponse handleException(DMakerException e,
                                               HttpServletRequest request){
        log.error("errorCode: {}, url: {}, message: {}",
                e.getDMakerErrorCode(), request.getRequestURI(), e.getDetailMessage());
        return DMakerErrorResponse.builder()
                .errorCode(e.getDMakerErrorCode())
                .errorMessage(e.getDetailMessage())
                .build();
    }

    @ExceptionHandler(value={
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentNotValidException.class
    }) // HttpReq~Exception: e.g. POST 맵핑이 된 곳에 GET을 시도한다던가?
    public DMakerErrorResponse handleBadRequest(
            Exception e, HttpServletRequest request
    ){
        log.error("url: {}, message: {}",
                request.getRequestURI(), e.getMessage());
        return DMakerErrorResponse.builder()
                .errorCode(DMakerErrorCode.INVALID_REQUEST)
                .errorMessage(DMakerErrorCode.INVALID_REQUEST.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class) // 무수히 많은 다른 exception들의 최후의 보루.
    public DMakerErrorResponse handleGeneralException(
            Exception e, HttpServletRequest request
    ){
        log.error("url: {}, message: {}",
                request.getRequestURI(), e.getMessage());
        return DMakerErrorResponse.builder()
                .errorCode(DMakerErrorCode.INTERNAL_SERVER_ERROR)
                .errorMessage(DMakerErrorCode.INTERNAL_SERVER_ERROR.getMessage())
                .build();
    }
}
