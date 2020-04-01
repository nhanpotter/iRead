package com.example.iread.books;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;

import com.example.iread.MyApplication;
import com.example.iread.R;

import javax.inject.Inject;

public class RatingFragment extends DialogFragment {
    private View rootView;

    private RatingBar ratingBar;

    private int bookId;
    private int userRating;

    @Inject
    RatingViewModel ratingViewModel;

    public RatingFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MyApplication) MyApplication.applicationContext).appComponent.inject(this);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.fragment_addrating, null);
        ratingBar = rootView.findViewById(R.id.ratingBar2);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b){
                userRating = (int) ratingBar.getRating();
            }
        });

        builder.setTitle("Rating")
                .setView(rootView)
                .setPositiveButton(R.string.send_rating, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ratingViewModel.postRating(bookId, userRating);

                        String message = "Thank you for rating the book!\n" +
                                "Your rating is " + userRating;
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(R.string.cancel_comment, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RatingFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_addrating, container, false);
        bookId = getArguments().getInt("id");

        return rootView;
    }
}
