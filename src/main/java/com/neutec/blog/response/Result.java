package com.neutec.blog.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.neutec.blog.enums.ResponseCode;
import com.neutec.blog.excption.ServiceException;
import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * API Response
 */
@Data
@JsonPropertyOrder({"systemTime", "statusCode", "message", "data"})
public class Result<T> {

    /**
     * 系統時間
     */
    private long systemTime = System.currentTimeMillis();
    /**
     * 回應資料
     */
    private T data;
    /**
     * car-plus API 回應代碼
     */
    private int statusCode;
    /**
     * 回應訊息
     */
    private String message;

    /**
     * 正常回覆 status code：0，no data
     */
    @NonNull
    public static Result<Object> success() {
        return success(null);
    }

    /**
     * 正常回覆 status code：0
     *
     * @param data 回應資料
     * @param <T>  資料類型
     */
    @NonNull
    public static <T> Result<T> success(@Nullable T data) {
        return of(data, ResponseCode.SUCCESS.getCode(), "OK");
    }

    /**
     * 常規回覆
     *
     * @param <T>     資料類型
     * @param code    回應代碼
     * @param message 回應訊息
     */
    @NonNull
    public static <T> Result<T> of(@Nullable T data, @NonNull int code, @Nullable String message) {
        Result<T> result = new Result<>();
        result.setData(data);
        result.setStatusCode(code);
        result.setMessage(message);

        return result;
    }

    /**
     * 常規回覆，no data
     *
     * @param code    回應代碼
     * @param message 回應訊息
     */
    @NonNull
    public static Result<Object> of(@NonNull int code, @Nullable String message) {
        return of(null, code, message);
    }

    /**
     * 錯誤回覆
     *
     * @param ex car-plus exception
     */
    @NonNull
    public static Result<Object> exception(@NonNull ServiceException ex) {
        return of(null, ResponseCode.FAIL.getCode(), ex.getMessage());
    }
}
