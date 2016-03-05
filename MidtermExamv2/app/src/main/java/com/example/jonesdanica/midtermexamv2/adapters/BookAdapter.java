package com.example.jonesdanica.midtermexamv2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jonesdanica.midtermexamv2.R;
import com.example.jonesdanica.midtermexamv2.entities.Book;

import java.util.List;

/**
 * Created by danica12 on 3/3/2016.
 */
public class BookAdapter extends ArrayAdapter<Book> {

    private Context mContext;
    private int mLayoutId;
    private List<Book> mBookList;

    public BookAdapter(Context context, int resource, List<Book> bookList) {
        super(context, resource, bookList);

        mContext = context;
        mLayoutId = resource;
        mBookList = bookList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            // Inflate the layout
            convertView = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);

            // create the view holder
            viewHolder = new ViewHolder();
            viewHolder.tvBookTitle = (TextView) convertView.findViewById(R.id.bookName);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Set the book data
        Book book = mBookList.get(position);

        if (book != null) {
            if (viewHolder.tvBookTitle != null) {
                viewHolder.tvBookTitle.setText(book.getTitle());
                if (book.isRead()) {
                    viewHolder.tvBookTitle.setTextColor(Color.parseColor("#B0171F"));
                    viewHolder.tvBookTitle.setPaintFlags(
                            viewHolder.tvBookTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    viewHolder.tvBookTitle.setTextColor(Color.DKGRAY);
                    viewHolder.tvBookTitle.setPaintFlags(
                            viewHolder.tvBookTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        }

        return convertView;
    }

    private static class ViewHolder {
        public TextView tvBookTitle;

            /*public ViewHolder (View view) {
                tvBookTitle = (TextView) view.findViewById(android.R.id.text1);
            }*/
    }
}