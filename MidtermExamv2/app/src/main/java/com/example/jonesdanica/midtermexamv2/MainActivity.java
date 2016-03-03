package com.example.jonesdanica.midtermexamv2;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jonesdanica.midtermexamv2.adapters.BookAdapter;
import com.example.jonesdanica.midtermexamv2.apis.BookApi;
import com.example.jonesdanica.midtermexamv2.constants.Constants;
import com.example.jonesdanica.midtermexamv2.entities.Book;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private ProgressBar mProgressBar;
    private TextView mtvProgress;
    private TextView mtvEmpty;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getParent(), BookDetailsActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        findViews();

        new FetchBookTask().execute();
    }

    public void findViews() {
        mtvEmpty = (TextView) findViewById(android.R.id.empty);
        mtvProgress = (TextView) findViewById(R.id.tvprogress);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mListView = (ListView) findViewById(R.id.list);
        mListView.setOnItemClickListener(this);

        //mListView.setAdapter(adapter);
    }

    public class FetchBookTask extends AsyncTask<String, Void, ArrayList<Book>> {
        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
            mtvProgress.setVisibility(View.VISIBLE);
            mtvEmpty.setVisibility(View.GONE);
        }

        @Override
        protected  ArrayList<Book> doInBackground(String... params) {
            //URI-based Retrieval
            Uri builtUri = Uri.parse(BookApi.BASE_URL).buildUpon()
                    .appendEncodedPath("api")
                    .appendEncodedPath("books")
                    .build();

            return BookApi.getBook(builtUri, "GET");

            // String-based Retrieval
            /*
             * String url = BookAPI.BASE_URL + "api/books";
             * return BookAPI.getBook(url, "GET");
            */
        }

        @Override
        protected void onPostExecute( ArrayList<Book> bookList) {

            mProgressBar.setVisibility(View.GONE);
            mtvProgress.setVisibility(View.GONE);

            if (bookList!=null) {
                adapter = new BookAdapter(
                        MainActivity.this,
                        R.layout.list_item, bookList);
                mListView.setAdapter(adapter);
                //adapter.addAll(bookList);
            }
            else {
                mtvEmpty.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, BookDetailsActivity.class);
        intent.putExtra(Constants.EXTRA_POSITION, position);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            new FetchBookTask().execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
