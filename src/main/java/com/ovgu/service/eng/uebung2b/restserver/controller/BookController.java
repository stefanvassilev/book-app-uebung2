package com.ovgu.service.eng.uebung2b.restserver.controller;


import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(BookController.API_V1_BOOKS)
public class BookController {


    public static final String API_V1_BOOKS = "/api/v1/books";
    @GetMapping("/")
    public HttpEntity<Book> getBook(@RequestParam String bookId) {


    }


}
