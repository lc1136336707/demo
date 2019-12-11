package com.example.demo.dto;

import com.example.demo.Exception.CustomizeErrorCode;
import lombok.Data;

@Data
public class ResultDTO {
    private int code;
    private String message;

    public ResultDTO(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultDTO errorOf(int code,String message){
        return new ResultDTO(code,message);
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return ResultDTO.errorOf(errorCode.getCode(),errorCode.getMessage());
    }
}
