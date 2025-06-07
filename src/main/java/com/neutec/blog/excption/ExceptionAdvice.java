package com.neutec.blog.excption;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {
    public ExceptionAdvice() {
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorResponse body = new ErrorResponse(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());

        return this.handleExceptionInternal(ex, body.throwableToResult(), new HttpHeaders(), HttpStatus.OK, request);
    }


    @NonNull
    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleException(@NonNull Throwable ex, @NonNull WebRequest request) {
        return this.handleExceptionInternal((Exception) ex, (Object) null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @NonNull
    @ExceptionHandler({ServiceException.class})
    protected ResponseEntity<Object> handleCarPlusException(@NonNull ServiceException ex, @NonNull WebRequest request) {
        ErrorResponse body = new ErrorResponse(ex.getMessage());
        return this.handleExceptionInternal(ex, body.throwableToResult(), new HttpHeaders(), HttpStatus.OK, request);
    }


    @NonNull
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception ex, @Nullable Object body, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        String errorInfo = ex.getClass().getName() + "：" + ex.getMessage();
        if (status.is5xxServerError()) {
            ex.printStackTrace();
        }

        Object _body = body;
        if (body == null) {
            _body = new ErrorResponse(errorInfo).throwableToResult();
        }

        return super.handleExceptionInternal(ex, _body, headers, status, request);
    }
}
