package com.jiangfendou.share.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * BusinessException.
 * @author jiangmh
 */
@Data
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1321372647800456847L;

    /** error code */
    protected ApiError apiError;

    /** error message */
    protected HttpStatus httpStatus;

    /**
     * Constructor.
     *
     */
    public BusinessException(HttpStatus httpStatus, ApiError apiError) {
        this.httpStatus = httpStatus;
        this.apiError = apiError;
    }

}
