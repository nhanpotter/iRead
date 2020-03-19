package com.example.iread.HomePage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.iread.BookListActivity;
import com.example.iread.R;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate:Started.");

        ImageButton top10books = findViewById(R.id.top10);
        ImageButton latestbooks = findViewById(R.id.imageButton6);
        Button show = findViewById(R.id.button4);
        ImageButton book1 = findViewById(R.id.bk1);
        ImageButton book2 = findViewById(R.id.bk2);
        ImageButton book3 = findViewById(R.id.bk3);
        ImageButton book4 = findViewById(R.id.bk4);
        ImageButton book5 = findViewById(R.id.bk5);
        ImageButton book6 = findViewById(R.id.bk6);
        ImageButton home = findViewById(R.id.imageButton16_hse);
        ImageButton lib = findViewById(R.id.imageButton20);
        ImageButton noti = findViewById(R.id.imageButton21);
        ImageButton user = findViewById(R.id.imageButton22);

        top10books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t10 = new Intent(HomeActivity.this, top10.class);
                startActivity(t10);
            }
        });

        latestbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lbooks = new Intent(HomeActivity.this, latest.class);
                startActivity(lbooks);
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showrec = new Intent(HomeActivity.this, BookListActivity.class);
                startActivity(showrec);
            }
        });


        book1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bok1 = new Intent(HomeActivity.this, bk1.class);
                startActivity(bok1);
            }
        });

        book2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bok2 = new Intent(HomeActivity.this, bk2.class);
                startActivity(bok2);
            }
        });

        book3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bok3 = new Intent(HomeActivity.this, bk3.class);
                startActivity(bok3);
            }
        });

        book4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bok4 = new Intent(HomeActivity.this, bk4.class);
                startActivity(bok4);
            }
        });

        book5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bok5 = new Intent(HomeActivity.this, bk5.class);
                startActivity(bok5);
            }
        });

        book6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bok6 = new Intent(HomeActivity.this, bk6.class);
                startActivity(bok6);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hse = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(hse);
            }
        });

        lib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent library = new Intent(HomeActivity.this, l1.class);
                startActivity(library);
            }
        });

        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notifications = new Intent(HomeActivity.this, n1.class);
                startActivity(notifications);
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent acct = new Intent(HomeActivity.this, u1.class);
                startActivity(acct);
            }
        });



    }

}
