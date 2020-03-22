package com.example.iread.books;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iread.R;

import java.util.ArrayList;
import java.util.List;

public class BooksFragment extends Fragment {

    BookListAdapter adapter;
    private List<Book> booksList;

    public BooksFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_books, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Data to populate the RecyclerView with
        fillBooksList();

        //Set up the RecyclerView
        setUpRecycleView();

        //SearchView
        setUpSearchView();
    }

    private void fillBooksList() {
        booksList = new ArrayList<>();
        booksList.add(new Book("1Blank"));
        booksList.add(new Book("2Blank"));
        booksList.add(new Book("3Blank"));
        booksList.add(new Book("4Blank"));
        booksList.add(new Book("5Blank"));
        booksList.add(new Book("6Blank"));
        booksList.add(new Book("7Blank"));
        booksList.add(new Book("8Blank"));
        booksList.add(new Book("9Blank"));
        booksList.add(new Book("10Blank"));
        booksList.add(new Book("11Blank"));
    }

    private void setUpRecycleView() {
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), numberOfColumns));

        adapter = new BookListAdapter(this.getContext(), booksList);
        recyclerView.setAdapter(adapter);
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
