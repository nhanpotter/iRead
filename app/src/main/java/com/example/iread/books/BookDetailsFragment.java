package com.example.iread.books;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iread.MyApplication;
import com.example.iread.R;

import java.util.List;

import javax.inject.Inject;

public class BookDetailsFragment extends Fragment {
    private View rootView;
    CommentAdapter adapter;

    private TextView title;
    private TextView author;
    private TextView description;
    private RatingBar ratingBarIndicator;
    private RatingBar ratingBarUser;
    private Button addCommentButton;

    private int userRating = 0;
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
        rootView = inflater.inflate(R.layout.fragment_bookdetails, container, false);
        id = getArguments().getInt("id");

        title = rootView.findViewById(R.id.title);
        author = rootView.findViewById(R.id.author);
        description = rootView.findViewById(R.id.description);

        ratingBarIndicator = rootView.findViewById(R.id.ratingBarIndicator);
        ratingBarUser = rootView.findViewById(R.id.ratingBar);

        addCommentButton = rootView.findViewById(R.id.addCommButton);

        displayDetails(id);

        ratingBarUser.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b){
                userRating = (int) ratingBar.getRating();
                ratingViewModel.postRating(id, userRating);

                ratingViewModel.getRating(id);
                ratingViewModel.ratingTotal.observe(getViewLifecycleOwner(), new Observer<Rating>() {
                    @Override
                    public void onChanged(Rating rating) {
                        ratingBarIndicator.setRating(rating.rating);
                    }
                });

                String message = "Thank you for rating the book!\n" +
                                "Your rating is " + userRating;
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                Navigation.findNavController(rootView).navigate(R.id.commentFragment, bundle);
            }
        });

        return rootView;
    }

    private void displayDetails(int id) {
        bookDetailsViewModel.getBook(id);
        bookDetailsViewModel.book.observe(getViewLifecycleOwner(), new Observer<Book>() {
            @Override
            public void onChanged(Book book) {
                title.setText(book.getBookTitle());
                author.setText(book.getAuthorName());

                ratingViewModel.getRating(id);
                ratingViewModel.ratingTotal.observe(getViewLifecycleOwner(), new Observer<Rating>() {
                    @Override
                    public void onChanged(Rating rating) {
                        ratingBarIndicator.setRating(rating.rating);
                    }
                });

                description.setMovementMethod(new ScrollingMovementMethod());
                description.setText(book.getSummary());

                commentViewModel.getCommentList(id);
                commentViewModel.commentList.observe(getViewLifecycleOwner(), new Observer<List<Comment>>() {
                    @Override
                    public void onChanged(List<Comment> commentList) {
                        setupCommentRecyclerView(commentList);
                    }
                });
            }
        });


    }

    private void setupCommentRecyclerView(List<Comment> commentList) {
        RecyclerView recyclerView = getView().findViewById(R.id.commentRecyclerView);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), numberOfColumns));
        adapter = new CommentAdapter(this.getContext(), commentList);

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
