package com.ovgu.book.server.bookserver.controller;

import com.ovgu.book.server.bookserver.entity.BookEntity;
import com.ovgu.book.server.bookserver.repository.BookRepository;
import exception.BookNotFoundException;
import exception.BookServerException;
import main.java.model.Book;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController(BookRestController.API_V1_BOOKS)
public class BookRestController {
    static final String API_V1_BOOKS = "api/v1/books";

    private static final Logger LOGGER = LoggerFactory.getLogger(BookRestController.class);

    @Autowired
    private BookRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{isbn}/")
    public ResponseEntity<Book> getBook(@RequestParam("isbn") String isbn) throws BookNotFoundException {
        LOGGER.info("retrieving book with isbn:", isbn);
        BookEntity entity = repository
                .findById(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));

        return ResponseEntity.ok(convertToDto(entity));
    }


    @GetMapping("/author/{author}/{page}/{size}/")
    public List<Book> getBooksByAuthor(@RequestParam(value = "author") String authorName,
                                       @RequestParam(value = "page", defaultValue = "1") int page,
                                       @RequestParam(value = "size", defaultValue = "100") int size) {

        LOGGER.info("retrieving books by author:", authorName);
        return repository.findAllByAuthor(authorName, PageRequest.of(page, size)).get().map(this::convertToDto).collect(Collectors.toList());
    }


    @PostMapping
    public ResponseEntity uploadBook(@RequestBody Book book) {
        BookEntity bookEntity = convertToEntity(book);

        LOGGER.info("uploading book", bookEntity);
        if (repository.existsById(book.getIsbn())) {
            throw new BookServerException(HttpStatus.NOT_FOUND.value(), "book with id" + book.getIsbn() + "not found");
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity deleteBook(@RequestParam String isbn) {
        LOGGER.info("deleting book", isbn);
        repository.deleteById(isbn);
        return ResponseEntity.noContent().build();
    }


    private Book convertToDto(BookEntity entity) {
        return modelMapper.map(entity, Book.class);
    }

    private BookEntity convertToEntity(Book book) {
        return modelMapper.map(book, BookEntity.class);
    }

}
