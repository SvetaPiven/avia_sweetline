package com.avia.exception;
import com.google.maps.errors.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestError extends ApiError {

    public BadRequestError() {
        super();
    }
}
