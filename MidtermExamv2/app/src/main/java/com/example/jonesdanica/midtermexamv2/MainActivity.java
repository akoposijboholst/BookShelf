package com.example.jonesdanica.midtermexamv2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
    private FloatingActionButton fab;
    private ArrayList<Book> bookArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BookDetailsActivity.class);
                intent.putExtra(Constants.EXTRA_ACTION, Constants.ADD_BOOKDETAIL);
                startActivity(intent);
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
            fab.setVisibility(View.GONE);
            mListView.setVisibility(View.GONE);
        }

        @Override
        protected  ArrayList<Book> doInBackground(String... params) {
            return BookApi.getBook();
        }

        @Override
        protected void onPostExecute( ArrayList<Book> bookList) {

            bookArrayList = bookList;
            mProgressBar.setVisibility(View.GONE);
            mtvProgress.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);

            if (bookList!=null) {
                adapter = new BookAdapter(
                        MainActivity.this,
                        R.layout.list_item, bookList);

                mListView.setAdapter(adapter);
                mListView.setVisibility(View.VISIBLE);
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
        intent.putExtra(Constants.EXTRA_POSITION, bookArrayList.get(position).getId());
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
        } else if (id == R.id.action_search) {

        }

        return super.onOptionsItemSelected(item);
    }
}
