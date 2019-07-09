package com.ovgu.book.server.bookserver.controller;


import com.ovgu.book.server.bookserver.exception.BookNotFoundException;
import com.ovgu.book.server.bookserver.exception.BookServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice(basePackageClasses = com.ovgu.book.server.bookserver.controller.BookRestController.class)
public class BookControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookControllerAdvice.class);

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> generalException(Exception e, WebRequest request) {
        LOGGER.error("unable to handle request", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(value = BookServerException.class)
    public ResponseEntity<Object> bookServerExeption(BookServerException e, WebRequest request) {
        LOGGER.error("unable to handle request", e);
        return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    }

    @ExceptionHandler(value = BookNotFoundException.class)
    public ResponseEntity<Object> bookNotFoundExeption(BookNotFoundException e, WebRequest request) {
        LOGGER.error("book not found", e, request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

}
