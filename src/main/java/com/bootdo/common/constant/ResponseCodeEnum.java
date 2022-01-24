package com.bootdo.common.constant;


public enum ResponseCodeEnum {

    /**
     * 响应状态码
     */
    SUCCESS(200, "请求成功"),
    FAIL(500, "请求异常"),

    FAIL_FILE_UPLOAD_ERROR(6000, "文件上传失败！"),
    USER_NOT_EXIST(6001, "用户不存在"),
    CASE_APPLY_ERROR(6002, "已申请该患者的病例"),
    CASE_APPLY_NO_ROLE3(6003, "该用户非病人"),
    USER_NO_EXIST_CASE(6004, "该用户没有录入病例"),
    USER_NO_EXIST_QS(6005, "该用户非亲属");

    private Integer code;

    private String msg;

    ResponseCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
