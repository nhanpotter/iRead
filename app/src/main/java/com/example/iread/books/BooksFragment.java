package com.example.iread.books;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iread.MyApplication;
import com.example.iread.R;
import com.example.iread.utils.CustomProgressDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class BooksFragment extends Fragment {
    private View rootView;
    BookListAdapter adapter;
    @Inject
    BooksViewModel booksViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        ((MyApplication)MyApplication.applicationContext).appComponent.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_books, container, false);
        CustomProgressDialog dialog = new CustomProgressDialog(getContext());

        booksViewModel.getBooksList();

        booksViewModel.booksList.observe(getViewLifecycleOwner(), new Observer<List<Book>>() {

            @Override
            public void onChanged(List<Book> books) {
                setUpRecycleView(books);
            }
        });

        booksViewModel.error.observe(getViewLifecycleOwner(), new Observer<String> () {
            @Override
            public void onChanged(String s) {
                Snackbar.make(rootView, s, Snackbar.LENGTH_LONG).show();
            }
        });

        booksViewModel.progress.observe(getViewLifecycleOwner(), new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean aBoolean) {
                dialog.show(aBoolean);
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Data to populate the RecyclerView with
        super.onViewCreated(view, savedInstanceState);
        //SearchView
        setUpSearchView();
    }


    private void setUpRecycleView(List<Book> booksList) {
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), numberOfColumns));

        adapter = new BookListAdapter(this.getContext(), booksList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setUpSearchView() {
        SearchView searchView = getView().findViewById(R.id.searchView2);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}
