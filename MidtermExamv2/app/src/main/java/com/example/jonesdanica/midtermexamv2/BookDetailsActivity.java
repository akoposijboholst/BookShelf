package com.example.jonesdanica.midtermexamv2;

import android.app.LoaderManager;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class BookDetailsActivity extends AppCompatActivity{

    private EditText mtvTitle;
    private EditText mtvGenre;
    private EditText mtvAuthor;
    private CheckBox mcbIsRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        findViews();
    }

    public void findViews() {
        mtvTitle = (EditText) findViewById(R.id.etTitle);
        mtvAuthor = (EditText) findViewById(R.id.etAuthor);
        mtvGenre = (EditText) findViewById(R.id.etGenre);
        mcbIsRead = (CheckBox) findViewById(R.id.cbIsRead);
    }


}
