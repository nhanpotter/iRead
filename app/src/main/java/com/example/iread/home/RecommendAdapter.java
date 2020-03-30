package com.example.iread.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iread.R;
import com.example.iread.books.Book;
import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {

    private List<Book> recommendedList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public RecommendAdapter(Context context, List<Book> recommendedList, ItemClickListener mClickListener) {
        this.mInflater = LayoutInflater.from(context);

        this.recommendedList = recommendedList;

        this.mClickListener = mClickListener;
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
        holder.title.setText(recommendedList.get(position).getBookTitle());
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return recommendedList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageButton thumbnail;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView2);
            thumbnail = itemView.findViewById(R.id.imageButton4);
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
