package com.ovgu.book.client.internal;


import com.ovgu.book.common.model.BookPage;

import java.util.Iterator;

public class BookPageIter implements Iterable<BookPageIter> {

    private static final int DEFAULT_PAGE_SIZE = 100;
    private int currentPageNumber;
    private int entries;
    private boolean hasNext;
    private BookClient bookClient;
    private String authorName;

    private BookPageIter(BookClient bookClient, String authorName, int page) {
        this.bookClient = bookClient;
        this.authorName = authorName;

        BookPage curPage = bookClient.getPage(authorName, page, DEFAULT_PAGE_SIZE);
        this.hasNext = curPage.isNext();
        this.currentPageNumber = curPage.getCurrentPage();
        this.entries = curPage.getEntries();
    }

    BookPageIter(BookClient bookClient, String authorName) {
        this(bookClient, authorName, 0);
    }

    @Override
    public Iterator<BookPageIter> iterator() {
        return new Iterator<BookPageIter>() {
            @Override
            public boolean hasNext() {
                return hasNext;
            }

            @Override
            public BookPageIter next() {
                return new BookPageIter(bookClient, authorName, ++currentPageNumber);
            }
        };
    }


    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    public int getEntries() {
        return entries;
    }

    public boolean isHasNext() {
        return hasNext;
    }
}
