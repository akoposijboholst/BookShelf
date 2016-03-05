package com.example.jonesdanica.midtermexamv2.apis;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.jonesdanica.midtermexamv2.entities.Book;
import com.example.jonesdanica.midtermexamv2.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by danica12 on 3/3/2016.
 */
public class BookApi {
    public static final String BASE_URL = "http://joseniandroid.herokuapp.com";
    private static final String TAG_ID = "_id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_GENRE = "genre";
    private static final String TAG_AUTHOR = "author";
    private static final String TAG_ISREAD = "isRead";

    private static final String TAG_NEWBOOK = "Book";

    public static ArrayList<Book> getBook() {
        Uri builtUri = Uri.parse(BookApi.BASE_URL).buildUpon()
                .appendEncodedPath("api")
                .appendEncodedPath("books")
                .build();

        String json = HttpUtils.GET(builtUri);
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        ArrayList<Book> bookList = new ArrayList<>();
        try {
            JSONArray jsonArr = new JSONArray(json);
            int arrSize = jsonArr.length();

            for (int i = 0; i < arrSize; i++) {
                JSONObject jsonObject = jsonArr.getJSONObject(i);
                String id = jsonObject.getString(TAG_ID),
                        title = jsonObject.getString(TAG_TITLE),
                        genre = jsonObject.getString(TAG_GENRE),
                        author = jsonObject.getString(TAG_AUTHOR);
                boolean isRead = jsonObject.getBoolean(TAG_ISREAD);

                bookList.add(new Book(id, title, genre, author, isRead));

            }
            return bookList;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("DANICA_PRETTY", e.toString());
            return null;
        }
    }

    public static Book getCertainBook(String id) {
        Uri builtUri = Uri.parse(BookApi.BASE_URL).buildUpon()
                .appendEncodedPath("api")
                .appendEncodedPath("books")
                .appendEncodedPath(id)
                .build();

        String json = HttpUtils.GET(builtUri);
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            JSONObject basic = new JSONObject(json);
//            int code = basic.getInt("code");
            JSONObject obj = basic.getJSONObject("result");
            Log.d("PRETTY_ME", obj.toString());
            String json_id = obj.getString(TAG_ID),
                    title = obj.getString(TAG_TITLE),
                    genre = obj.getString(TAG_GENRE),
                    author = obj.getString(TAG_AUTHOR);
            boolean isRead = obj.getBoolean(TAG_ISREAD);

            return new Book(json_id, title, genre, author, isRead);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("PRETTY_ME", e.toString());
            return null;
        }
    }

    public static ArrayList<Book> getBookGenre(String _genre) {
        Uri builtUri = Uri.parse(BookApi.BASE_URL).buildUpon()
                .appendEncodedPath("api")
                .appendEncodedPath("books")
                .appendQueryParameter("genre", _genre)
                .build();

        String json = HttpUtils.GET(builtUri);
        Log.d("Boholst", json);
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        ArrayList<Book> bookList = new ArrayList<>();
        try {
            JSONArray jsonArr = new JSONArray(json);
            int arrSize = jsonArr.length();

            for (int i = 0; i < arrSize; i++) {
                JSONObject jsonObject = jsonArr.getJSONObject(i);
                String id = jsonObject.getString(TAG_ID),
                        title = jsonObject.getString(TAG_TITLE),
                        genre = jsonObject.getString(TAG_GENRE),
                        author = jsonObject.getString(TAG_AUTHOR);
                boolean isRead = jsonObject.getBoolean(TAG_ISREAD);

                bookList.add(new Book(id, title, genre, author, isRead));

            }
            return bookList;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("DANICA_PRETTY", e.toString());
            return null;
        }
    }

    public static ArrayList<Book> getBookAuthor(String _author) {
        Uri builtUri = Uri.parse(BookApi.BASE_URL).buildUpon()
                .appendEncodedPath("api")
                .appendEncodedPath("books")
                .appendQueryParameter("author", _author)
                .build();

        String json = HttpUtils.GET(builtUri);
        Log.d("Boholst", json);
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        ArrayList<Book> bookList = new ArrayList<>();
        try {
            JSONArray jsonArr = new JSONArray(json);
            int arrSize = jsonArr.length();

            for (int i = 0; i < arrSize; i++) {
                JSONObject jsonObject = jsonArr.getJSONObject(i);
                String id = jsonObject.getString(TAG_ID),
                        title = jsonObject.getString(TAG_TITLE),
                        genre = jsonObject.getString(TAG_GENRE),
                        author = jsonObject.getString(TAG_AUTHOR);
                boolean isRead = jsonObject.getBoolean(TAG_ISREAD);

                bookList.add(new Book(id, title, genre, author, isRead));

            }
            return bookList;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("DANICA_PRETTY", e.toString());
            return null;
        }
    }

    public static void addBook(Book book) {
        Uri uri = Uri.parse(BookApi.BASE_URL).buildUpon()
                .appendEncodedPath("api")
                .appendEncodedPath("books")
                .build();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(TAG_NEWBOOK, book);
            Log.d("POST_RESPONSE", HttpUtils.POST(uri, jsonObject));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void updateBook(Book book) {
        Uri uri = Uri.parse(BookApi.BASE_URL).buildUpon()
                .appendEncodedPath("api")
                .appendEncodedPath("books")
                .appendEncodedPath(book.getId())
                .build();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("_id", book.getId());
            jsonObject.put("genre", book.getGenre());
            jsonObject.put("title", book.getTitle());
            jsonObject.put("author", book.getAuthor());
            jsonObject.put("isRead", book.isRead());
            HttpUtils.PATCH(uri, jsonObject);
//            Log.d("PUT", HttpUtils.PUT(uri, jsonObject));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void searchBook(Uri uri, Book book) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(TAG_NEWBOOK, book);
            Log.d("POST_RESPONSE", HttpUtils.POST(uri, jsonObject));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void deleteBook(Book book) {
        Uri uri = Uri.parse(BookApi.BASE_URL).buildUpon()
                .appendEncodedPath("api")
                .appendEncodedPath("books")
                .appendPath(book.getId())
                .build();
        Log.d("Delete", uri.toString());
        HttpUtils.DELETE(uri);
//            Log.d("POST_RESPONSE", HttpUtils.POST(uri, jsonObject));
    }

}
