package com.izor066.android.mediatracker.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.izor066.android.mediatracker.R;
import com.izor066.android.mediatracker.api.model.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by igor on 5/12/15.
 */
public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchResultsViewHolder>  {

    public List<Book> mResultList = new ArrayList<>();

    public SearchResultsAdapter(List<Book> searchResultList) {
        mResultList = searchResultList;
    }

    @Override
    public SearchResultsViewHolder onCreateViewHolder(ViewGroup viewGroup, int index) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_result_book, viewGroup, false);
        return new SearchResultsViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(SearchResultsViewHolder searchResultsViewHolder, int i) {
        searchResultsViewHolder.update(mResultList.get(i));
    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    class SearchResultsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView bookCover;
        TextView bookTitle;
        TextView bookAuthor;
        ImageButton addBook;
        Book book;


        public SearchResultsViewHolder(View itemView) {
            super(itemView);
            bookCover = (ImageView) itemView.findViewById(R.id.iv_search_img_placeholder);
            bookTitle = (TextView) itemView.findViewById(R.id.tv_search_book_title);
            bookAuthor = (TextView) itemView.findViewById(R.id.tv_search_book_author);
            addBook = (ImageButton) itemView.findViewById(R.id.ib_search_add);
            addBook.setOnClickListener(this);
        }

        void update(Book book) {
            this.book = book;
            bookTitle.setText(book.getTitle());
            bookAuthor.setText(book.getAuthor());

            Context context = bookCover.getContext();

            Picasso.with(context)
                    .load(book.getCoverImgUri())
                    .into(bookCover);
        }


        @Override
        public void onClick(View v) {
            Toast.makeText(itemView.getContext(), book.getTitle(), Toast.LENGTH_SHORT).show();
        }
    }



}
