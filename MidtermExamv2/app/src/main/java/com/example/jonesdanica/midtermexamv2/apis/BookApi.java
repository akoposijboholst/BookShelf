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
    public static final String BASE_URL     = "http://joseniandroid.herokuapp.com";

    private static final String TAG_TITLE        = "title";
    private static final String TAG_ISREAD   = "isRead";

    // URI-based Retrieval
    public static ArrayList<Book> getBook(Uri uri, @NonNull String requestMethod) {
        String json = HttpUtils.GET(uri);

        if (TextUtils.isEmpty(json)) {
            return null;
        }

        ArrayList<Book> booklist = new ArrayList<>();

        try {
            // 1) Convert the json string response into an actual JSON Object
            JSONArray jsonArr = new JSONArray(json);

            int arrSize = jsonArr.length();

            for (int i = 0; i < arrSize; i++) {
                JSONObject jsonObject = jsonArr.getJSONObject(i);
                String title = jsonObject.getString(TAG_TITLE);
                boolean isRead = jsonObject.getBoolean(TAG_ISREAD);

                booklist.add(new Book(title, isRead));
                Log.d("DANICA", jsonObject.toString());
            }

            return booklist;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    // STRING-based Retrieval
    public static ArrayList<Book> getBook(String url, @NonNull String requestMethod) {
        String json = HttpUtils.GET(url);

        if (TextUtils.isEmpty(json)) {
            return null;
        }

        ArrayList<Book> booklist = new ArrayList<>();

        try {
            // 1) Convert the json string response into an actual JSON Object
            JSONArray jsonArr = new JSONArray(json);

            int arrSize = jsonArr.length();

            for (int i = 0; i < arrSize; i++) {
                JSONObject jsonObject = jsonArr.getJSONObject(i);
                String title = jsonObject.getString(TAG_TITLE);
                boolean isRead = jsonObject.getBoolean(TAG_ISREAD);

                booklist.add(new Book(title, isRead));
                Log.d("DANICA", jsonObject.toString());
            }

            return booklist;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
