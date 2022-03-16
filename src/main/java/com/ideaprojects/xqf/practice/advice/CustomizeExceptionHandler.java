package com.ideaprojects.xqf.practice.advice;

import com.alibaba.fastjson.JSON;
import com.ideaprojects.xqf.practice.dto.ResultDTO;
import com.ideaprojects.xqf.practice.exception.CustomizeErrorCode;
import com.ideaprojects.xqf.practice.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(
            HttpServletRequest request,
            HttpServletResponse response,
            Throwable e,
            Model model) {
        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            ResultDTO resultDTO;
            if(e instanceof CustomizeException){
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            }else{
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try{
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            }catch (IOException ioe){

            }
            return null;
        }else{
            if(e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            }else{
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }

            return new ModelAndView("error");
        }
    }
}
