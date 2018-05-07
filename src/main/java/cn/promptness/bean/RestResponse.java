package cn.promptness.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author : Lynn
 * @Date : 2018-03-24 14:13
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse<T> {

    private int code;
    private String msg;
    private T result;

    public static <T> RestResponse<T> success() {
        return new RestResponse<T>();
    }

    public static <T> RestResponse<T> success(T result) {
        RestResponse<T> response = new RestResponse<T>();
        response.setResult(result);
        return response;
    }

    public static <T> RestResponse<T> error(RestCode code) {
        return new RestResponse<T>(code.code, code.msg);
    }

    public RestResponse() {
        this(RestCode.SUCCESS.code, RestCode.SUCCESS.msg);
    }

    public RestResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
