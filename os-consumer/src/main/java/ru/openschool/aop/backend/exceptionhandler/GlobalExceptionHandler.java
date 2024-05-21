package ru.openschool.aop.backend.exceptionhandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.openschool.aop.backend.exception.ValidateException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ValidateException e) {
        return new ResponseEntity<>(new AppError(e.getStatus().value(), e.getMessage()), e.getStatus());
    }
}
