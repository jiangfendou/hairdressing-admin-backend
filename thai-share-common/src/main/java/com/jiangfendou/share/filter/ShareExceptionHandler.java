package com.jiangfendou.share.filter;

import com.jiangfendou.share.common.ApiResponse;
import com.jiangfendou.share.common.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * PaymentExceptionHandler.
 * @author jiangmh
 */
@Slf4j
@RestControllerAdvice
public class ShareExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse> handleException(BusinessException e) {
        return new ResponseEntity<>(ApiResponse.failed(e.getHttpStatus(), e.getApiError()), e.getHttpStatus());
    }
}
