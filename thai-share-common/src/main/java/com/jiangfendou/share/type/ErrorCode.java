package com.jiangfendou.share.type;

/**
 * ErrorCode.
 * @author jiangmh
 */
public enum ErrorCode {

    // ---------------------------------   base error code    ------------------------
    /**
     * 系统异常
     * */
    SYSTEM_ERROR ("s.bse.0001", "系统异常。"),


    // ----------------------------------  vehicle error code ------------------------
    /**
     * 没有找到指定的目标数据
     * */
    NOT_FOUND_TARGET_ERROR ("s.veh.1001", "没有找到指定的目标数据。"),
    // ----------------------------------  user error code    ------------------------
    PASSWORD_ERROR("e.user.2001", "Password Error."),

    // ----------------------------------  plan error code    ------------------------
    // ----------------------------------  partner error code ------------------------
    // ----------------------------------  gateway error code ------------------------
    /**
     * Token不能我空。
     * */
    TOKEN_CANNOT_BE_EMPTY ("s.gate.5001", "Token不能我空。"),

    /**
     * Token无效。
     * */
    INVALID_TOKEN ("s.gate.5002", "Token无效。");

    /** error code */
    private final String code;
    /** error message */
    private final String message;

    /**
     * Constructor.
     *
     * @param code code of error code
     */
    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}
