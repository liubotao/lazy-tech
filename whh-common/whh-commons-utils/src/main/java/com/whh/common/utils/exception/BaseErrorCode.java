package com.whh.common.utils.exception;

/**
 * 1** 参数类错误<br/>
 * 2**<br/>
 * 3** 计算逻辑类错误<br/>
 * 4**<br/>
 * 5**<br/>
 *
 * @author huahui.wu <br/>
 */
public enum BaseErrorCode {

    /**
     * 必要参数丢失
     */
    RequiredArgumentsMissing(105, "Missing required arguments"),

    /**
     * 参数不合法
     */
    InvalidArguments(110, "Invalid arguments"),

    /**
     * 方法不存在
     */
    MethodNotExist(140, "No Such Method"),

    /**
     * 计算签名错误
     */
    SignError(302, "Check sign error"),

    /**
     * 解析数据发生异常
     */
    ParseException(304, "Parse Data Exception"),

    /**
     * 检查权限失败
     */
    AccessUnauthorized(330, "Access Unauthorized"),

    ApiCallError(540, "Api Call Error"),

    HostException(700, "Host Process Exception"),


    DATA_EXISTS(604, "{0}已存在，不允许重复"),
    DATA_NOT_EXISTS(605, "{0}不存在"),
    DATA_REQUIRED(606, "{0}不能为空"),
    INVALID_ARGS(609, "{0}为不合法的参数，建议值：{1}"),
    DATA_NOT_NUMBER(611, "传入的参数必须为数字：{0}"),
    DATA_MAX_LENGTH(612, "{0}长度不能大于{1}个字符"),
    DATA_MIN_LENGTH(613, "{0}长度不能小于{1}个字符"),
    DATA_RANGE(614, "{0}必须是 {1} 到 {2}之间的数字");


    private int code;
    private String text;

    private BaseErrorCode(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public int getErrorCode() {
        return this.code;
    }

    public String getErrorText() {
        return this.text;
    }

    public String toString() {
        return String.valueOf(getErrorCode()) + ":" + getErrorText();
    }

    public static BaseErrorCode valueOf(int code) {
        for (BaseErrorCode err : BaseErrorCode.values()) {
            if (err.getErrorCode() == code) {
                return err;
            }
        }
        return null;
    }
}
