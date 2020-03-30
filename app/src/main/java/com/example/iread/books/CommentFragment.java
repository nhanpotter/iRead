package com.example.iread.books;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import com.example.iread.MyApplication;
import com.example.iread.R;

import java.util.List;

import javax.inject.Inject;

public class CommentFragment extends Fragment {
    private View rootView;

    private TextView commentField;
    private Button confirmButton;

    private int bookId;

    @Inject
    CommentViewModel commentViewModel;

    public CommentFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MyApplication) MyApplication.applicationContext).appComponent.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_addcomment, container, false);
        bookId = getArguments().getInt("id");

        commentField = rootView.findViewById(R.id.comment);
        confirmButton = rootView.findViewById(R.id.confirm);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentField.getText().toString();
                commentViewModel.postComment(bookId, comment);
                Navigation.findNavController(rootView).navigate(R.id.booksFragment);
            }
        });


        return rootView;
    }
}
