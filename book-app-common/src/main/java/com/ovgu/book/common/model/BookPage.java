package com.ovgu.book.common.model;

import java.util.List;

public class BookPage {

    private boolean next;
    private int currentPage;
    private int entries;
    private List<Book> books;

    public BookPage(boolean next, int currentPage, int entries, List<Book> books) {
        this.next = next;
        this.currentPage = currentPage;
        this.entries = entries;
        this.books = books;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public boolean isNext() {
        return next;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getEntries() {
        return entries;
    }

    public void setEntries(int entries) {
        this.entries = entries;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}

