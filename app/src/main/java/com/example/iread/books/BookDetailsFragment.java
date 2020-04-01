package com.example.iread.books;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iread.MyApplication;
import com.example.iread.R;
import com.example.iread.utils.CustomProgressDialog;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import com.example.iread.utils.DownloadTask;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.folioreader.FolioReader;
import com.folioreader.model.HighLight;
import com.folioreader.model.locators.ReadLocator;
import com.folioreader.util.OnHighlightListener;
import com.folioreader.util.ReadLocatorListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class BookDetailsFragment extends Fragment implements OnHighlightListener, ReadLocatorListener, FolioReader.OnClosedListener {
    private View rootView;
    CommentAdapter adapter;

    private ImageView cover;
    private TextView title;
    private TextView author;
    private TextView description;
    private RatingBar ratingBarIndicator;
    private RatingBar ratingBarUser;
    private Button addCommentButton;
    private Button addRatingButton;

    private Button readButton;
    private String resourceUrl = null;
    private File bookFile;

    private int userRating = 0;
    private int id;

    // Folio Reader
    private FolioReader folioReader;

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

        cover = rootView.findViewById(R.id.thumbnail);
        title = rootView.findViewById(R.id.title);
        author = rootView.findViewById(R.id.author);
        description = rootView.findViewById(R.id.description);

        ratingBarIndicator = rootView.findViewById(R.id.ratingBarIndicator);
        ratingBarUser = rootView.findViewById(R.id.ratingBar);

        addRatingButton = rootView.findViewById(R.id.addRateButton);
        addCommentButton = rootView.findViewById(R.id.addCommButton);
        readButton = rootView.findViewById(R.id.readButton);
        bookFile = new File(getContext().getExternalFilesDir(null), id+".epub");

        if (checkEpubExist()) {
            readButton.setText(R.string.read);
        }
        folioReader = FolioReader.get()
                .setOnHighlightListener(this)
                .setReadLocatorListener(this)
                .setOnClosedListener(this);

        displayDetails(id);

        addRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                Navigation.findNavController(rootView).navigate(R.id.ratingFragment, bundle);
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

        readButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!checkEpubExist()) {
                    new DownloadTask(getContext(), readButton, resourceUrl, id);
                } else {
                    folioReader.openBook(bookFile.getPath());
                }
            }
        });

        return rootView;
    }

    private void displayDetails(int id) {
        CustomProgressDialog dialog = new CustomProgressDialog(getContext());
        bookDetailsViewModel.getBook(id);
        bookDetailsViewModel.book.observe(getViewLifecycleOwner(), new Observer<Book>() {
            @Override
            public void onChanged(Book book) {
                String thumbnailURL = book.cover.replace("http", "https");
                Picasso.get().load(thumbnailURL).fit().into(cover);
                title.setText(book.getBookTitle());
                author.setText(book.getAuthorName());

                ratingViewModel.getRating(id);
                ratingBarIndicator.setRating(book.overallRating);
                ratingViewModel.rating.observe(getViewLifecycleOwner(), new Observer<Rating>() {
                    @Override
                    public void onChanged(Rating rating) {
                        ratingBarUser.setRating(rating.rating);
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
                resourceUrl = book.getResourceUrl();
            }
        });

        bookDetailsViewModel.error.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(rootView, s, Snackbar.LENGTH_LONG).show();
            }
        });

        bookDetailsViewModel.progress.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                dialog.show(aBoolean);
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

    private boolean checkEpubExist() {
        return bookFile.exists();
    }


    @Override
    public void onFolioReaderClosed() {

    }

    @Override
    public void onHighlight(HighLight highlight, HighLight.HighLightAction type) {

    }

    @Override
    public void saveReadLocator(ReadLocator readLocator) {

    }
}
