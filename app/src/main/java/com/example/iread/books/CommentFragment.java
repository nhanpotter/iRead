package com.example.iread.books;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import com.example.iread.MyApplication;
import com.example.iread.R;

import java.util.List;

import javax.inject.Inject;

public class CommentFragment extends DialogFragment {
    private View rootView;

    private TextView commentField;

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
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.fragment_addcomment, null);
        commentField = rootView.findViewById(R.id.comment);

        builder.setTitle("Comment")
                .setView(rootView)
                .setPositiveButton(R.string.send_comment, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String comment = commentField.getText().toString();
                        commentViewModel.postComment(bookId, comment);
                    }
                })
                .setNegativeButton(R.string.cancel_comment, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CommentFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_addcomment, container, false);
        bookId = getArguments().getInt("id");

        return rootView;
    }
}
