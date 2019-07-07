package com.ovgu.integration.suites;


import com.ovgu.book.client.internal.BookClient;
import com.ovgu.book.client.internal.BookClientBuilder;
import com.ovgu.book.client.internal.BookPageIter;
import com.ovgu.book.common.model.Book;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class FullBookAppWithClientIT {

    private final String baseUrl = "http://it-book-app.cfapps.io/api/v1/books";

    private BookClient client = BookClientBuilder.newBuilder(baseUrl).build();


    @Test
    void TestGettingBookInfo() {

        Book testBook = new Book();
        testBook.setAuthor("test Author");
        testBook.setContent("Lorem ipsum".getBytes());
        testBook.setIsbn("testIsbn" + UUID.randomUUID().toString());

        assertDoesNotThrow(() -> client.uploadBook(testBook));

        Book retrievedBook = client.getBookDetails(testBook.getIsbn());

        assertNotNull(retrievedBook);
        assertEquals(testBook.getAuthor(), retrievedBook.getAuthor());
        assertEquals(new String(testBook.getContent()), new String(retrievedBook.getContent()));
        assertEquals(testBook.getIsbn(), retrievedBook.getIsbn());
    }

    @Test
    void TestGettingBookPage() {
        String testAuthor = "testAuthorPagination" + UUID.randomUUID().toString();

        IntStream.range(0, 200).forEach(i -> {
            Book testBook = new Book();
            testBook.setAuthor(testAuthor);
            testBook.setContent("Lorem ipsum".getBytes());
            testBook.setIsbn("testIsbn" + UUID.randomUUID().toString() + "#" + i);
            assertDoesNotThrow(() -> client.uploadBook(testBook));
        });

        BookPageIter iter = client.getBooksByAuthor(testAuthor);

        assertEquals(100, iter.getEntries());
        assertEquals(0, iter.getCurrentPageNumber());
        assertTrue(iter.isHasNext());

        BookPageIter next = iter.iterator().next();
        assertFalse(next.iterator().hasNext());

    }

    @Test
    void TestUploadingBook() {

        Book testBook = new Book();
        testBook.setAuthor("test Author");
        testBook.setContent("Lorem ipsum".getBytes());
        testBook.setIsbn("testIsbn" + UUID.randomUUID().toString());

        assertDoesNotThrow(() -> client.uploadBook(testBook));
    }

    @Test
    void TestDeletingBook() {
        Book testBook = new Book();
        testBook.setAuthor("testAuthor");
        testBook.setIsbn("testIsbn" + UUID.randomUUID().toString());

        assertDoesNotThrow(() -> client.uploadBook(testBook));
        assertDoesNotThrow(() -> client.deleteBook(testBook.getIsbn()));

    }

}
