package com.ovgu.book.server.bookserver.controller;

import com.ovgu.book.server.bookserver.entity.BookEntity;
import com.ovgu.book.server.bookserver.repository.BookRepository;
import com.ovgu.book.common.model.Book;
import com.ovgu.book.common.model.BookPage;
import com.ovgu.book.server.bookserver.exception.BookNotFoundException;
import com.ovgu.book.server.bookserver.exception.BookServerException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;


@RestController
@RequestMapping(BookRestController.API_V1_BOOKS)
public class BookRestController {
    static final String API_V1_BOOKS = "/api/v1/books";

    private static final Logger LOGGER = LoggerFactory.getLogger(BookRestController.class);

    @Autowired
    private BookRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> getBook(@PathVariable("isbn") String isbn) throws BookNotFoundException {
        LOGGER.info("retrieving book with isbn:" + isbn);
        BookEntity entity = repository
                .findById(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));

        return ResponseEntity.ok(convertToDto(entity));
    }


    @GetMapping("/author/{author}/page/{page}/size/{size}")
    public ResponseEntity<BookPage> getBooksByAuthor(@PathVariable("author") String authorName,
                                                     @PathVariable("page") int page,
                                                     @PathVariable("size") int size) {
        LOGGER.info("retrieving books by author:", authorName);

        if (size > 400) {
            return ResponseEntity.badRequest().build();

        }

        Page<BookEntity> books = repository.findAllByAuthor(authorName, PageRequest.of(page, size));
        BookPage bookPage = new BookPage(books.hasNext(),
                books.getNumber(),
                books.getNumberOfElements(),
                books.get().map(this::convertToDto).collect(Collectors.toList()));

        return ResponseEntity.ok(bookPage);
    }


    @PostMapping
    public ResponseEntity uploadBook(@RequestBody Book book) {
        BookEntity bookEntity = convertToEntity(book);

        if (repository.existsById(book.getIsbn())) {
            throw new BookServerException(HttpStatus.CONFLICT.value(), "book with id" + book.getIsbn() + "not found");
        }

        LOGGER.info("saving book"+ bookEntity);
        repository.save(bookEntity);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/isbn/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn") String isbn) {
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
