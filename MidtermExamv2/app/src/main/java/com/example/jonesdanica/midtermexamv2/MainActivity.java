package com.example.jonesdanica.midtermexamv2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jonesdanica.midtermexamv2.adapters.BookAdapter;
import com.example.jonesdanica.midtermexamv2.apis.BookApi;
import com.example.jonesdanica.midtermexamv2.constants.Constants;
import com.example.jonesdanica.midtermexamv2.entities.Book;
import com.example.jonesdanica.midtermexamv2.fragments.SearchAlertDialog;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchAlertDialog.OnDataPass, AdapterView.OnItemClickListener {
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
    }

    public void findViews() {
        mtvEmpty = (TextView) findViewById(android.R.id.empty);
        mtvProgress = (TextView) findViewById(R.id.tvprogress);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mListView = (ListView) findViewById(R.id.list);
        mListView.setOnItemClickListener(this);

        //mListView.setAdapter(adapter);
        FetchBookTaskExecute();
    }

    protected void FetchBookTaskExecute(String... params) {
        new FetchBookTask().execute(params);
    }

    @Override
    public void onDataPass(String... data) {
        FetchBookTaskExecute(data);
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
        protected ArrayList<Book> doInBackground(String... params) {
            if (params.length != 0) {
                if (params[0] == "Genre") {
                    Log.d("FaloHalo", params[1]);
                    return BookApi.getBookGenre(params[1]);
                } else {
                    return BookApi.getBookAuthor(params[1]);
                }
            }
            return BookApi.getBook();
        }

        @Override
        protected void onPostExecute(ArrayList<Book> bookList) {

            bookArrayList = bookList;
            mProgressBar.setVisibility(View.GONE);
            mtvProgress.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);

            if (!bookList.isEmpty()) {
                adapter = new BookAdapter(
                        MainActivity.this,
                        R.layout.list_item, bookList);

                mListView.setAdapter(adapter);
                mListView.setVisibility(View.VISIBLE);
                //adapter.addAll(bookList);
                Log.d("Convince Me", "Convince");
            } else {
                Log.d("Im Empty", "Im Ampety");
                mtvEmpty.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, BookDetailsActivity.class);
        intent.putExtra(Constants.EXTRA_POSITION, bookArrayList.get(position).getId());
        intent.putExtra(Constants.EXTRA_ACTION, Constants.VIEW_BOOKDETAIL);
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
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            FetchBookTaskExecute();
            return true;
        } else if (id == R.id.action_search) {
            SearchAlertDialog searchAlertDialog = new SearchAlertDialog();
            searchAlertDialog.show(getFragmentManager(), "Search Dialog");
//            if (!searchAlertDialog.isInLayout()) {
////            String searchBy = searchAlertDialog.getItemSelected();
////            String toQuery = searchAlertDialog.getInputtedItem();
//                String searchBy = searchAlertDialog.query;
//                String toQuery = searchAlertDialog.toQuery;
//                Log.d("Passed", searchBy + toQuery);
//                FetchBookTaskExecute(new String[]{searchBy, toQuery});
//            }
        }

        return super.onOptionsItemSelected(item);
    }
}
