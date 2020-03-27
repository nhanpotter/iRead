package com.example.iread.books;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.iread.MyApplication;
import com.example.iread.R;

import javax.inject.Inject;

public class BookDetailsFragment extends Fragment {
    private TextView title;
    private TextView author;
    private TextView description;
    private RatingBar ratingBarStars;
    private float userRating = 0;
    private int id;

    @Inject
    BookDetailsViewModel bookDetailsViewModel;

    public BookDetailsFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MyApplication) MyApplication.applicationContext).appComponent.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bookdetails, container, false);
        title = v.findViewById(R.id.title);
        author = v.findViewById(R.id.author);
        description = v.findViewById(R.id.description);

        ratingBarStars = v.findViewById(R.id.ratingBar);

        id = getArguments().getInt("id");
        displayDetails(id);

        ratingBarStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b){
                userRating = ratingBar.getRating();
                String message = "Thank you for rating the book!\n" +
                                "Your rating is " + userRating;
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }

    private void displayDetails(int id) {
        bookDetailsViewModel.getBook(id);
        bookDetailsViewModel.book.observe(getViewLifecycleOwner(), new Observer<Book>() {
            @Override
            public void onChanged(Book book) {
                title.setText(book.getBookTitle());
                author.setText(book.getAuthorName());

                description.setMovementMethod(new ScrollingMovementMethod());
                description.setText(book.getSummary());
            }
        });
    }
}
