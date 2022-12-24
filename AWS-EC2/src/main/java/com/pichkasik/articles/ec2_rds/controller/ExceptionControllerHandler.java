package com.pichkasik.articles.ec2_rds.controller;

import com.pichkasik.articles.ec2_rds.exceptions.NotFoundException;
import com.pichkasik.articles.ec2_rds.exceptions.ResourceAlreadyExist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerHandler {


    @ExceptionHandler({NotFoundException.class, ResourceAlreadyExist.class})
    public ResponseEntity<String> handleNotFoundException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
