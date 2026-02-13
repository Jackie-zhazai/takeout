package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }
    @ExceptionHandler//如果没有这个注解，这个方法就只是一个普通的 Java 方法，
    // Spring 框架在捕获到异常时，看不到这个方法，所以它只能把异常抛出去，导致你看到 HTTP 500 和控制台的一大堆报错。
public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        //duplicate entry 'zhangsan' for key 'employee.idx username'
        String message=ex.getMessage();
        if(message.contains("Duplicate entry")){
            String[] split=message.split( " ");
            String username=split[2];
            String msg= MessageConstant.ALREADY_EXISTS +username;
            return Result.error(msg);

        }
else{ return Result.error(MessageConstant.UNKNOWN_ERROR);}
}
}
