package com.ovgu.book.client.internal;


class BookClientException extends RuntimeException {

    public BookClientException(String message) {
        super(message);
    }

    public BookClientException(String error, Throwable e) {
        super(error, e);

    }
}
