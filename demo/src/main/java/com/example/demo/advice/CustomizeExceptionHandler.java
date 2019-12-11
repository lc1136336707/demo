package com.example.demo.advice;

import com.alibaba.fastjson.JSON;
import com.example.demo.Exception.CustomizeErrorCode;
import com.example.demo.Exception.CustomizeException;
import com.example.demo.dto.ResultDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable ex, Model model, HttpServletRequest request, HttpServletResponse response){
        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            ResultDTO resultDTO = null;
            if(ex instanceof CustomizeException){
                resultDTO = ResultDTO.errorOf(((CustomizeException) ex).getCode(),ex.getMessage());
            }else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.PAGE_NOT_FOUND);
            }
            try {
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                response.setStatus(200);
                Writer writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(ex instanceof CustomizeException){
            model.addAttribute("message",ex.getMessage());
        }else {
            model.addAttribute("message",CustomizeErrorCode.PAGE_NOT_FOUND.getMessage());
        }
        return new ModelAndView("error");
    }
}
