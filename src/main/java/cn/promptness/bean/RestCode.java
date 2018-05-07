package cn.promptness.bean;

/**
 * @Author : Lynn
 * @Date : 2018-03-24 14:14
 */
public enum RestCode {

    SUCCESS(00000,"处理成功"),
    UNKNOWN_ERROR(10000,"用户服务异常"),
    WRONG_PAGE(100100,"页码不合法"),
    USER_NOT_FOUND(100101,"用户未找到"),
    ILLEGAL_PARAMS(10102,"参数不合法"),
    TOKEN_INVALID(100002,"TOKEN失效"),
    USER_NOT_EXIST(100003,"用户不存在"),
    PHONE_NUMBER_IS_EXIST(100004,"手机号码已经注册"),
    LACK_PARAMS(100105,"缺少参数");


    public final int code;
    public final String msg;
    private RestCode(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

}
