package com.udemy.configurations;

import com.udemy.exceptions.AlreadyFoundException;
import com.udemy.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = NullPointerException.class)
    public String nullPointerExceptionHandler(){
        log.warn("Null pointer Exception occured");
        return "Null Pointer Exception Occurred";
    }
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public String exceptionHandler(){
        log.warn("An exception Occurred");
        return "An Exception Occurred";
    }
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = AlreadyFoundException.class)
    public String alreadyFoundException(){
        log.warn("Record Already Found Exception");
        return "Record Already Found";
    }
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = NotFoundException.class)
    public String notFoundException(){
        log.warn("Record Not Found Exception");
        return "Record Not Found";
    }
}
