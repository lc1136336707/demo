package com.example.demo.Exception;

public enum CustomizeErrorCode {
    SUCCESS(200,"请求成功"),
    QUESTION_NOT_FOUND(2001,"问题不存在或已删除！"),
    USER_NOT_LOGIN(2002,"用户未登录！"),
    COMMENT_MOT_FOUND(2003,"评论不存在或已删除！"),
    PAGE_NOT_FOUND(2004,"页面不存在或已删除！"),
    COMMENT_TYPE_NOT_FOUND(2005,"回复类型不存在！"),

    ;

    private int code;
    private String message;

    CustomizeErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
