package com.ovgu.book.client.internal;

import java.io.IOException;

public class BookClientException extends RuntimeException{

    public BookClientException(String message) {
        super(message);
    }

    public BookClientException(String error, Throwable e) {
        super(error, e);

    }
}
