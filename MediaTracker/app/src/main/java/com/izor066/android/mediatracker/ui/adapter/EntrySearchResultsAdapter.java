package com.izor066.android.mediatracker.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.izor066.android.mediatracker.R;
import com.izor066.android.mediatracker.api.model.Book;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by igor on 24/12/15.
 */
public class EntrySearchResultsAdapter extends RecyclerView.Adapter<EntrySearchResultsAdapter.EntrySearchResultsViewHolder>{

    public interface OnEntryClickListener {
        void onEntryClick(Book book);

    }

    private final OnEntryClickListener listener;
    public List<Book> mResultList = new ArrayList<>();

    public EntrySearchResultsAdapter(List<Book> searchResultList, OnEntryClickListener listener) {
        mResultList = searchResultList;
        this.listener = listener;
    }

    @Override
    public EntrySearchResultsViewHolder onCreateViewHolder(ViewGroup viewGroup, int index) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book, viewGroup, false);
        return new EntrySearchResultsViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(EntrySearchResultsViewHolder entrySearchResultsViewHolder, int i) {
        entrySearchResultsViewHolder.update(mResultList.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    static class EntrySearchResultsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView bookCover;
        TextView bookTitle;
        TextView bookAuthor;
        Book book;
        OnEntryClickListener listener;


        public EntrySearchResultsViewHolder(View itemView) {
            super(itemView);
            bookCover = (ImageView) itemView.findViewById(R.id.iv_img_placeholder);
            bookTitle = (TextView) itemView.findViewById(R.id.tv_book_title);
            bookAuthor = (TextView) itemView.findViewById(R.id.tv_book_author);
            itemView.setOnClickListener(this);
        }

        void update(Book book, OnEntryClickListener listener) {
            this.book = book;
            bookTitle.setText(book.getTitle());
            bookAuthor.setText(book.getAuthor());
            this.listener = listener;

            Context context = bookCover.getContext();

            if (book.getCoverImgUri().startsWith("http") || book.getCoverImgUri().startsWith("android.resource"))   {
                Picasso.with(context)
                        .load(book.getCoverImgUri())
                        .into(bookCover);
            } else {
                Picasso.with(context)
                        .load(new File(book.getCoverImgUri()))
                        .into(bookCover);
            }
        }


        @Override
        public void onClick(View v) {

                listener.onEntryClick(book);

        }
    }


}
