package com.example.iread.books;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iread.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<Comment> commentList;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public CommentAdapter(Context context, List<Comment> commentList) {
        this.mInflater = LayoutInflater.from(context);

        this.commentList = commentList;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.comment_view, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.commentUsername.setText(commentList.get(position).user.username);
        String dateInString = commentList.get(position).time;
        String formattedDate = null;
        try {
            formattedDate = dateConvert(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.commentTime.setText(formattedDate);
        holder.comment.setText(commentList.get(position).comment);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return commentList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView commentUsername;
        TextView commentTime;
        TextView comment;

        ViewHolder(View itemView) {
            super(itemView);
            commentUsername = itemView.findViewById(R.id.commentUsername);
            commentTime = itemView.findViewById(R.id.commentTime);
            comment = itemView.findViewById(R.id.comment);
        }

    }

    private String dateConvert(String dateString) throws ParseException {
        SimpleDateFormat initialFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX");
        SimpleDateFormat afterFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = initialFormatter.parse(dateString);
        String formattedDate = afterFormatter.format(date);
        return formattedDate;
    }
}
