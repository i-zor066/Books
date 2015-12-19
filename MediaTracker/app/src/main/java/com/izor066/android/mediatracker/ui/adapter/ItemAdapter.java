package com.izor066.android.mediatracker.ui.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.izor066.android.mediatracker.MediaTrackerApplication;
import com.izor066.android.mediatracker.R;
import com.izor066.android.mediatracker.api.model.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by igor on 13/11/15.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemAdapterViewHolder> {

    public enum SortCriteria {
        ADDED,
        TITLE,
        AUTHOR
    }

    private SortCriteria currentSortCriteria = SortCriteria.ADDED;

    // private String currentSortCriteria = "added"; // "title", "author"
    private final OnBookClickListener listener;
    private boolean isAscending = true;

    public interface OnBookClickListener {
        void onBookClick(Book book);
    }

    public void changeSortCriteria(SortCriteria currentSortCriteria) {
        this.currentSortCriteria = currentSortCriteria;
    }

    public void changeSortOrder() {
        isAscending = !isAscending;
    }


    public ItemAdapter(OnBookClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ItemAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int index) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book, viewGroup, false);
        return new ItemAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ItemAdapterViewHolder itemAdapterViewHolder, int i) {
        List<Book> bookList = new ArrayList<Book>();
        bookList = MediaTrackerApplication.getSharedDataSource().getAllBooks();


        switch (currentSortCriteria) {
            case TITLE:
                Collections.sort(bookList, new Comparator<Book>() {
                    public int compare(Book b1, Book b2) {
                        return b1.getTitle().compareToIgnoreCase(b2.getTitle());
                    }
                });
                break;
            case AUTHOR:
                Collections.sort(bookList, new Comparator<Book>() {
                    public int compare(Book b1, Book b2) {
                        return b1.getAuthor().compareToIgnoreCase(b2.getAuthor());
                    }
                });
                break;
            case ADDED:
                Collections.sort(bookList, new Comparator<Book>() {
                    public int compare(Book b1, Book b2) {
                        return Long.compare(b1.getTimeAdded(), b2.getTimeAdded()); // ToDo: is there a way to do it to support API below 19
                    }
                });
                break;
            default:
                throw new IllegalArgumentException("Cannot sort by: " + currentSortCriteria);
        }

        if (!isAscending) {
            Collections.reverse(bookList);
        }


        itemAdapterViewHolder.update(bookList.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return MediaTrackerApplication.getSharedDataSource().getAllBooks().size();
    }

    static class ItemAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView bookCover;
        TextView bookTitle;
        TextView bookAuthor;
        Book book;
        OnBookClickListener listener;


        public ItemAdapterViewHolder(View itemView) {
            super(itemView);
            bookCover = (ImageView) itemView.findViewById(R.id.iv_img_placeholder);
            bookTitle = (TextView) itemView.findViewById(R.id.tv_book_title);
            bookAuthor = (TextView) itemView.findViewById(R.id.tv_book_author);
            itemView.setOnClickListener(this);
        }

        void update(Book book, OnBookClickListener listener) {
            this.book = book;
            bookTitle.setText(book.getTitle());
            bookAuthor.setText(book.getAuthor());
            this.listener = listener;

            Context context = bookCover.getContext();

            Picasso.with(context)
                    .load(book.getCoverImgUri())
                    .into(bookCover);
        }

        // OnClickListener

        @Override
        public void onClick(View v) {
            listener.onBookClick(book);
            // Toast.makeText(itemView.getContext(), book.getTitle(), Toast.LENGTH_SHORT).show();
        }
    }


}
