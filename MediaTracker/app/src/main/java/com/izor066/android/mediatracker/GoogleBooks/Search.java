package com.izor066.android.mediatracker.GoogleBooks;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volumes;
import com.izor066.android.mediatracker.util.LogUtil;

import java.io.IOException;

/**
 * Created by igor on 4/12/15.
 */

public class Search {
    public static final HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();

    public static final JsonFactory JSON_FACTORY = new AndroidJsonFactory();

    public static String apiKey = "AIzaSyBnNQWLjvDuaUSXSbXz0oKRbcfCeVe9EN8";

    private static Books books;


    static {
        books = new Books.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
            public void initialize(HttpRequest request) throws IOException {
            }
        }).setApplicationName("MediaTracker").build();
    }

    public static Volumes searchVolumes(String queryTerm) {
        try {
            Books.Volumes.List search = books.volumes().list(queryTerm)
                    .setFields("items(volumeInfo,userInfo)")
                    .setKey(apiKey)
                    .setMaxResults(40L)
                    .setOrderBy("relevance");
            return search.execute();
        } catch (GoogleJsonResponseException e) {
            LogUtil.d("There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage() + " " + e.getDetails().getErrors() + " " + e);
        } catch (IOException e) {
            LogUtil.d("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            LogUtil.d(t);
        }

        return null;
    }
}
