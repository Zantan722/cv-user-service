package com.neutec.blog.excption;

import com.neutec.blog.enums.ResponseCode;
import com.neutec.blog.response.Result;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data

@NoArgsConstructor
public class ErrorResponse {
    private int statusCode;
    private String message;
    private Date timestamp;

    public ErrorResponse(String message) {
        this.statusCode = ResponseCode.FAIL.getCode();
        this.message = message;
        this.timestamp = new Date();
    }

    public Result throwableToResult() {
        return Result.of(statusCode, message);
    }
}
