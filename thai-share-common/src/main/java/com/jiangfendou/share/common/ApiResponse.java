package com.jiangfendou.share.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private HttpStatus httpStatus;
    private ApiError apiError;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private ApiResponse() {

    }

    protected ApiResponse(HttpStatus httpStatus, ApiError apiError, T data) {
        this.httpStatus = httpStatus;
        this.apiError = apiError;
        this.data = data;
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(HttpStatus.OK, null, null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(HttpStatus.OK, null, data);
    }

    public static <T> ApiResponse<T> failed(HttpStatus httpStatus) {
        return new ApiResponse<>(httpStatus, null, null);
    }

    public static <T> ApiResponse<T> failed(HttpStatus httpStatus, ApiError apiError) {
        return new ApiResponse<>(httpStatus, apiError, null);
    }
    //getter setter omitted


    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ApiError getApiError() {
        return apiError;
    }

    public void setApiError(ApiError apiError) {
        this.apiError = apiError;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}

