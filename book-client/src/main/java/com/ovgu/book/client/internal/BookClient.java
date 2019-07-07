package com.ovgu.book.client.internal;

import com.google.gson.*;
import com.ovgu.book.common.model.Book;
import com.ovgu.book.common.model.BookPage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

public class BookClient {

    private String baseUrl;
    private CloseableHttpClient httpClient;
    private Gson gson;

    private Set<Integer> acceptedStatusCodesGet = new HashSet<Integer>() {{
        add(HttpStatus.SC_OK);

    }};

    BookClient(String baseUrl) {
        this.baseUrl = baseUrl;

        httpClient = HttpClients.createDefault();
        GsonBuilder builder = new GsonBuilder();

        gson = builder.registerTypeHierarchyAdapter(byte[].class, new ByteArrayToBase64TypeAdapter()).create();

    }

    private static class ByteArrayToBase64TypeAdapter implements JsonDeserializer<byte[]> {

        @Override
        public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Base64.getDecoder().decode(json.getAsString());
        }
    }


    /**
     * Returns details about a book if available.
     *
     * @param isbn the isbn of a book
     * @return book or @null if book not found
     */
    public Book getBookDetails(String isbn) {
        HttpEntity entity = null;
        try (CloseableHttpResponse response = httpClient.execute(new HttpGet(baseUrl + "/isbn/" + isbn))) {
            if (!acceptedStatusCodesGet.contains(response.getStatusLine().getStatusCode())) {
                throw new BookClientException("unknown status code: " + response);
            }

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                return null;
            }

            entity = response.getEntity();
            return gson.fromJson(EntityUtils.toString(entity, StandardCharsets.UTF_8.name()), Book.class);
        } catch (IOException e) {
            throw new BookClientException(isbn);
        } finally {
            EntityUtils.consumeQuietly(entity);
        }

    }

    /**
     * @param authorName author of books collection
     */
    public BookPageIter getBooksByAuthor(String authorName) {
        return new BookPageIter(this, authorName);
    }

    BookPage getPage(String authorName, int page, int size) {
        HttpEntity entity = null;
        try (CloseableHttpResponse response = httpClient.execute(
                new HttpGet(baseUrl + "/author/" + authorName + "/page/" + page + "/size/" + size))) {

            if (!acceptedStatusCodesGet.contains(response.getStatusLine().getStatusCode())) {
                throw new BookClientException("unknown status code" + response.getStatusLine());
            }

            entity = response.getEntity();
            String body = EntityUtils.toString(entity, StandardCharsets.UTF_8.name());

            System.out.println(body);

            return gson.fromJson(body, BookPage.class);


        } catch (IOException e) {
            throw new BookClientException("error", e);
        } finally {
            EntityUtils.consumeQuietly(entity);
        }
    }

    /**
     * @param book to upload
     */
    public void uploadBook(Book book) {
        HttpPost req = new HttpPost(baseUrl);
        req.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
        try {
            req.setEntity(new StringEntity(gson.toJson(book)));
        } catch (UnsupportedEncodingException e) {
            throw new BookClientException("error", e);
        }

        try (CloseableHttpResponse response = httpClient.execute(req)) {
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_NO_CONTENT) {
                throw new BookClientException("could not upload book: " + response);
            }

        } catch (IOException e) {
            throw new BookClientException("error", e);
        }
    }

    /**
     * Delete book based on isbn.
     *
     * @param isbn of book to delete
     */
    public void deleteBook(String isbn) {

        HttpDelete httpDelete = new HttpDelete(baseUrl + "/isbn/" + isbn);

        try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_NO_CONTENT) {
                throw new BookClientException("could not delete book");
            }

        } catch (IOException e) {
            throw new BookClientException("error", e);
        }

    }
}
