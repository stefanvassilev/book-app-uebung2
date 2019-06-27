package com.ovgu.book.server.bookserver.controller;


import exception.BookServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class BookControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookControllerAdvice.class);

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> generalException(Exception e, WebRequest request) {
        LOGGER.error("unable to handle request", e, request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(value = BookServerException.class)
    public ResponseEntity<Object> bookServerExeption(BookServerException e, WebRequest request) {
        LOGGER.error("unable to handle request", e, request);
        return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    }

}
