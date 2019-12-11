package com.example.demo.enums;

public enum CommentType {
    QUESTION(1),
    COMMENT(2);
    private Integer type;

    public static boolean isExist(Integer type) {
        for (CommentType value : CommentType.values()) {
            if(value.getType() == type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentType(Integer type) {
        this.type = type;
    }

}
