package com.example.jonesdanica.midtermexamv2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import com.example.jonesdanica.midtermexamv2.apis.BookApi;
import com.example.jonesdanica.midtermexamv2.constants.Constants;
import com.example.jonesdanica.midtermexamv2.entities.Book;
import com.example.jonesdanica.midtermexamv2.fragments.GetBookDataFragment;

public class BookDetailsActivity extends AppCompatActivity {

    private EditText mtvTitle;
    private EditText mtvGenre;
    private EditText mtvAuthor;
    private CheckBox mcbIsRead;
    private Book mBook;
    private int mAction;
    private String bookID;
    private String choice;
    private boolean toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        findViews();
        toggle = true;
        Intent intent = getIntent();
        if (intent == null) {
            throw new RuntimeException("BookDetailsActivity is expecting an int extra passed by Intent");
        }
        bookID = intent.getStringExtra(Constants.EXTRA_POSITION);
        mAction = intent.getIntExtra(Constants.EXTRA_ACTION, 0);
        switch (mAction) {
            case Constants.ADD_BOOKDETAIL: {
                break;
            }
            case Constants.EDIT_BOOKDETAIL: {
                if (getSupportActionBar() != null) {
                    new FetchBookTask().execute();
                }
                break;
            }
            case Constants.VIEW_BOOKDETAIL: {
                if (getSupportActionBar() != null) {
                    new FetchBookTask().execute();
                }
                break;
            }
            default: {
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

    public class FetchBookTask extends AsyncTask<String, Void, Void> {
        private GetBookDataFragment mFragment;

        @Override
        protected void onPreExecute() {
            Bundle bundle = new Bundle();
            mFragment = new GetBookDataFragment();
            switch (mAction) {
                case Constants.VIEW_BOOKDETAIL: {
                    bundle.putInt(Constants.EXTRA_ACTION, Constants.VIEW_BOOKDETAIL);
                    mFragment.setArguments(bundle);
                    mFragment.show(getFragmentManager(), "Fragment");
                    break;
                }
                case Constants.EDIT_BOOKDETAIL: {
                    bundle.putInt(Constants.EXTRA_ACTION, Constants.EDIT_BOOKDETAIL);
                    mFragment.setArguments(bundle);
                    mFragment.show(getFragmentManager(), "Fragment");
                    break;
                }
                case Constants.DELETE_BOOKDETAIL: {
                    bundle.putInt(Constants.EXTRA_ACTION, Constants.DELETE_BOOKDETAIL);
                    mFragment.setArguments(bundle);
                    mFragment.show(getFragmentManager(), "Fragment");
                    break;
                }
                case Constants.ADD_BOOKDETAIL: {
                    bundle.putInt(Constants.EXTRA_ACTION, Constants.ADD_BOOKDETAIL);
                    mFragment.setArguments(bundle);
                    mFragment.show(getFragmentManager(), "Fragment");
                    break;
                }
            }
        }

        @Override
        protected Void doInBackground(String... params) {
            switch (mAction) {
                case Constants.VIEW_BOOKDETAIL: {
                    mBook = BookApi.getCertainBook(bookID);
                    break;
                }
                case Constants.EDIT_BOOKDETAIL: {
                    BookApi.updateBook(mBook);
                    break;
                }
                case Constants.DELETE_BOOKDETAIL: {
                    BookApi.deleteBook(mBook);
                }
                case Constants.ADD_BOOKDETAIL: {
                    BookApi.addBook(mBook);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            switch (mAction) {
                case Constants.VIEW_BOOKDETAIL: {

                    mFragment.dismiss();

                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(mBook.getTitle());
                    }

                    mtvAuthor.setText(mBook.getAuthor());
                    mtvGenre.setText(mBook.getGenre());
                    mtvTitle.setText(mBook.getTitle());

                    mtvAuthor.setEnabled(false);
                    mtvGenre.setEnabled(false);
                    mtvTitle.setEnabled(false);
                    if (mBook.isRead()) {
                        mcbIsRead.setChecked(true);
                        mcbIsRead.setEnabled(false);
                    }
                    break;
                }
                default: {
                    Intent intent = new Intent(BookDetailsActivity.this, MainActivity.class);
                    startActivity(intent);
                    mFragment.dismiss();
                    break;
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        int add = R.menu.menu_addbook,
                edit_view = R.menu.menu_getbookdetails;

        switch (mAction) {
            case Constants.ADD_BOOKDETAIL: {
                inflater.inflate(add, menu);
                return true;
            }
            case Constants.EDIT_BOOKDETAIL: {
                inflater.inflate(edit_view, menu);
                return true;
            }
            case Constants.VIEW_BOOKDETAIL: {
                inflater.inflate(edit_view, menu);
                return true;
            }
            default: {
                return false;
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Book book = mBook;
        if (id == R.id.toggle_edit_ok) {
            if (toggle) {
                item.setIcon(R.drawable.ic_done);
                mtvTitle.setEnabled(true);
                mtvGenre.setEnabled(true);
                mtvAuthor.setEnabled(true);
                mcbIsRead.setEnabled(true);
            } else {
                item.setIcon(R.drawable.ic_edit);
                book.setTitle(mtvTitle.getText().toString());
                book.setGenre(mtvGenre.getText().toString());
                book.setAuthor(mtvAuthor.getText().toString());
                book.setIsRead(mcbIsRead.isChecked());
                mAction = Constants.EDIT_BOOKDETAIL;
                new FetchBookTask().execute();
            }
            toggle = !toggle;
            return true;
        } else if (id == R.id.action_delete) {
            mAction = Constants.DELETE_BOOKDETAIL;
            new FetchBookTask().execute();
            return true;
        } else if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_ok) {
            mBook = new Book();
            mBook.setTitle(mtvTitle.getText().toString());
            mBook.setGenre(mtvGenre.getText().toString());
            mBook.setAuthor(mtvAuthor.getText().toString());
            mBook.setIsRead(mcbIsRead.isChecked());
            mAction = Constants.ADD_BOOKDETAIL;
            new FetchBookTask().execute();
        }

        return super.onOptionsItemSelected(item);
    }
}
