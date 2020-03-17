package com.example.iread;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity{

    BookListAdapter adapter;
    private List<Book> booksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookslist);

        // data to populate the RecyclerView with
        fillBooksList();

        // set up the RecyclerView
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
        booksList.add(new Book("12Blank"));
    }

    private void setUpRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new BookListAdapter(this, booksList);
        recyclerView.setAdapter(adapter);
    }

    private void setUpSearchView() {
        SearchView searchView = findViewById(R.id.searchView2);
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