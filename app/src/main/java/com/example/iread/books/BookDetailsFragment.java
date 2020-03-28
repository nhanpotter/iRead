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

import com.example.iread.MyApplication;
import com.example.iread.R;

import javax.inject.Inject;

public class BookDetailsFragment extends Fragment {
    private TextView title;
    private TextView author;
    private TextView description;
    private RatingBar ratingBarIndicator;
    private RatingBar ratingBarUser;
    private float userRating = 0;
    private int id;

    @Inject
    BookDetailsViewModel bookDetailsViewModel;
    @Inject
    CommentViewModel commentViewModel;
    @Inject
    RatingViewModel ratingViewModel;

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
        id = getArguments().getInt("id");

        title = v.findViewById(R.id.title);
        author = v.findViewById(R.id.author);
        description = v.findViewById(R.id.description);

        ratingBarIndicator = v.findViewById(R.id.ratingBarIndicator);
//        ratingViewModel.getRating(id);
//        ratingViewModel.ratingTotal.observe(getViewLifecycleOwner(), new Observer<Rating>() {
//            @Override
//            public void onChanged(Rating rating) {
//                ratingBarIndicator.setIsIndicator(false);
//                ratingBarIndicator.setRating(rating.rating);
//                ratingBarIndicator.setIsIndicator(true);
//            }
//        });

        ratingBarUser = v.findViewById(R.id.ratingBar);

        displayDetails(id);

        ratingBarUser.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b){
                userRating = ratingBar.getRating();
//                ratingViewModel.postRating(id, userRating);

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
