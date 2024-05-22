package com.lpdecastro.ecommerce.exception;

import com.lpdecastro.ecommerce.config.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppException extends RuntimeException {

    private HttpStatus httpStatus;
    private ErrorMessage errorMessage;
    private Object[] errorParams;

    public AppException(HttpStatus httpStatus, ErrorMessage errorMessage) {
        this(httpStatus, errorMessage, null);
    }
}
