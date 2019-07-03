package com.ovgu.book.server.bookserver.entity;

import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@ToString
public class BookEntity {
    public BookEntity() {
    }

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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
