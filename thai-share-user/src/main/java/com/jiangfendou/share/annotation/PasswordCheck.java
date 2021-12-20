package com.jiangfendou.share.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


import com.jiangfendou.share.common.ApiError;
import com.jiangfendou.share.common.BusinessException;
import com.jiangfendou.share.type.ErrorCode;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * The annotated element must be defined byte size.
 *
 * @author lin.duan
 */
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordCheck.PasswordCheckValidator.class)
public @interface PasswordCheck {

    Logger LOG = LoggerFactory.getLogger(PasswordCheck.class);

    /**
     * Message.
     *
     * @return Message explaining violation.
     */
    String message() default "password must contain 8 digits of letter case, number and special character combination";

    /**
     * Validator.
     */
    class PasswordCheckValidator implements ConstraintValidator<PasswordCheck, String> {

        public static final String PW_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W)[A-Za-z\\d\\W]{8,16}";

        /**
         * passwordCheck.
         */
        protected PasswordCheck passwordCheck;

        @Override
        public void initialize(PasswordCheck param) {
            passwordCheck = param;
        }

        @Override
        public boolean isValid(String str, ConstraintValidatorContext context) {

            if (str.matches(PW_PATTERN)) {
                return true;
            }
            LOG.info("isValid() " + ErrorCode.PASSWORD_ERROR.getMessage());
            throw new BusinessException(HttpStatus.CONFLICT,
                new ApiError(ErrorCode.PASSWORD_ERROR.getCode(), ErrorCode.PASSWORD_ERROR.getMessage()));
        }
    }

}
