package com.example.iread;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.example.iread.HomePage.HomeActivity;
import com.example.iread.HomePage.l1;
import com.example.iread.HomePage.n1;
import com.example.iread.HomePage.u1;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity{

    BookListAdapter adapter;
    private List<Book> booksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookslist);

        //Data to populate the RecyclerView with
        fillBooksList();

        //Set up the RecyclerView
        setUpRecycleView();

        //SearchView
        setUpSearchView();

        //Bottom navigation
        ImageButton home = findViewById(R.id.imageButton16_hse);
        ImageButton lib = findViewById(R.id.imageButton20);
        ImageButton noti = findViewById(R.id.imageButton21);
        ImageButton user = findViewById(R.id.imageButton22);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hse = new Intent(BookListActivity.this, HomeActivity.class);
                startActivity(hse);
            }
        });

        lib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent library = new Intent(BookListActivity.this, l1.class);
                startActivity(library);
            }
        });

        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notifications = new Intent(BookListActivity.this, n1.class);
                startActivity(notifications);
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent acct = new Intent(BookListActivity.this, u1.class);
                startActivity(acct);
            }
        });
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