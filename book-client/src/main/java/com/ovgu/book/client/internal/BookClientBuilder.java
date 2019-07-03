package com.ovgu.book.client.internal;


/**
 * Creates newBuilder for book client java client.
 */
public class BookClientBuilder {
    private String url;

    private BookClientBuilder(String baseApiUrl) {
        this.url = baseApiUrl;
    }

    /**
     * Create a new instance of the newBuilder class
     *
     * @param baseApiUrl baseApiUrl of the Api, where book service is currently active
     */
    public static BookClientBuilder newBuilder(String baseApiUrl) {
        return new BookClientBuilder(baseApiUrl);
    }

    /**
     * @return new non-thread safe instance BookClient
     */
    public BookClient build() {
        return new BookClient(url);
    }

}
