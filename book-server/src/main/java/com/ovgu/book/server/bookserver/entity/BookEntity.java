package com.ovgu.book.server.bookserver.entity;

import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@ToString
public class BookEntity {

    @Id
    private String isbn;
    private String author;
    @ToString.Exclude
    private byte[] content;

    public BookEntity(String isbn, String author, byte[] content) {
        this.isbn = isbn;
        this.author = author;
        this.content = content;
    }


}
