package com.example.iread.books;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iread.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> implements Filterable {

    private List<Book> booksList;
    private List<Book> booksListFull;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public BookListAdapter(Context context, List<Book> booksList, ItemClickListener mClickListener) {
        this.mInflater = LayoutInflater.from(context);

        this.booksList = booksList;
        this.booksListFull = new ArrayList<>(booksList);

        this.mClickListener = mClickListener;
    }

    public void setBooksList(List<Book> booksList) {
        this.booksList = booksList;
        this.booksListFull = new ArrayList<>(booksList);
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.book_view, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(booksList.get(position).getBookTitle());
        String coverURL = booksList.get(position).cover.replace("http", "https");
        Picasso.get().load(coverURL).fit().into(holder.thumbnail);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public Filter getFilter() {
        return filter;
    }
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Book> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                filteredList.addAll(booksListFull);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Book item : booksListFull) {
                    if(item.getBookTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            booksList.clear();
            booksList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageButton thumbnail;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView2);
            thumbnail = itemView.findViewById(R.id.imageButton4);
            thumbnail.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}