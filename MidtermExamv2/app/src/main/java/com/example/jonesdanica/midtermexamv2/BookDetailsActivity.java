package com.example.jonesdanica.midtermexamv2;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import com.example.jonesdanica.midtermexamv2.apis.BookApi;
import com.example.jonesdanica.midtermexamv2.constants.Constants;
import com.example.jonesdanica.midtermexamv2.entities.Book;

import java.util.ArrayList;

public class BookDetailsActivity extends AppCompatActivity{

    private EditText mtvTitle;
    private EditText mtvGenre;
    private EditText mtvAuthor;
    private CheckBox mcbIsRead;
    private Book mBook;
    private int mAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        findViews();

        Intent intent = getIntent();
        if (intent == null) {
            throw new RuntimeException("MovieDetailsActivity is expecting an int extra passed by Intent");
        }

        mAction = intent.getIntExtra(Constants.EXTRA_ACTION, 0);

        switch (mAction) {
            case Constants.ADD_BOOKDETAIL: {
                break;
            } case Constants.EDIT_BOOKDETAIL: {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(mBook.getTitle());
                }
                mBook = intent.getParcelableExtra(Constants.EXTRA_POSITION);
                if (mBook == null) {
                    throw new NullPointerException("No movie found at the passed position.");
                }
                new FetchBookTask().execute();
                break;
            } case Constants.VIEW_BOOKDETAIL: {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(mBook.getTitle());
                }
                mBook = intent.getParcelableExtra(Constants.EXTRA_POSITION);
                if (mBook == null) {
                    throw new NullPointerException("No movie found at the passed position.");
                }
                new FetchBookTask().execute();
                break;
            } default: {
                break;
            }
        }

    }

    public void findViews() {
        mtvTitle = (EditText) findViewById(R.id.etTitle);
        mtvAuthor = (EditText) findViewById(R.id.etAuthor);
        mtvGenre = (EditText) findViewById(R.id.etGenre);
        mcbIsRead = (CheckBox) findViewById(R.id.cbIsRead);

    }

    public class FetchBookTask extends AsyncTask<String, Void, ArrayList<Book>> {
        @Override
        protected void onPreExecute() {
            //DialogFragment
            //ProgressBar
            //TextView
        }

        @Override
        protected  ArrayList<Book> doInBackground(String... params) {
            return BookApi.getBook();
        }

        @Override
        protected void onPostExecute( ArrayList<Book> bookList) {
            mtvAuthor.setText(mBook.getAuthor());
            mtvGenre.setText(mBook.getGenre());
            mtvTitle.setText(mBook.getTitle());
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        switch (mAction) {
            case Constants.ADD_BOOKDETAIL: {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu_addbook, menu);
                return true;
            } case Constants.EDIT_BOOKDETAIL: {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu_getbookdetails, menu);
                return true;
            } case Constants.VIEW_BOOKDETAIL: {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu_getbookdetails, menu);
                return true;
            } default: {
                return false;
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            //update book item
            //back to MainActivity
            return true;
        } else if (id == R.id.action_delete) {
            //delete book item
            //back to MainActivity
            return true;
        } else if (id == R.id.action_ok) {
            //input or update book item
            //back to MainActivity
            return true;
        } else if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
