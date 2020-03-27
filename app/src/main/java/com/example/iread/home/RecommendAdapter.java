package com.example.iread.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iread.R;
import com.example.iread.books.Book;
import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {

    private Context mContext;
    private List<Book> recommendedList;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public RecommendAdapter(Context context, List<Book> recommendedList) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);

        this.recommendedList = recommendedList;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.book_view, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.thumbnail.setOnClickListener(v ->
                Toast.makeText(mContext, "Test Click" + viewHolder.getAdapterPosition(),Toast.LENGTH_SHORT).show());
        return viewHolder;
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
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageButton thumbnail;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView2);
            thumbnail = itemView.findViewById(R.id.imageButton4);
        }
    }
}
