package com.ciwei.eat.bean;

/**
 * @author LIUQI
 * @date 2019/12/11 18:27
 */
public class Result {

    private String code;
    private String msg;

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Result success(){
        return new Result("000","succes");
    }

    public String getCode() {

        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
