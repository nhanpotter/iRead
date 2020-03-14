package com.example.homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate:Started.");

        @SuppressLint("WrongViewCast") ImageButton top10books = (ImageButton)findViewById(R.id.top10);
        @SuppressLint("WrongViewCast") ImageButton latestbooks = (ImageButton)findViewById(R.id.imageButton6);
        @SuppressLint("WrongViewCast") Button show = (Button)findViewById(R.id.button4);
        @SuppressLint("WrongViewCast") ImageButton book1 = (ImageButton)findViewById(R.id.bk1);
        @SuppressLint("WrongViewCast") ImageButton book2 = (ImageButton)findViewById(R.id.bk2);
        @SuppressLint("WrongViewCast") ImageButton book3 = (ImageButton)findViewById(R.id.bk3);
        @SuppressLint("WrongViewCast") ImageButton book4 = (ImageButton)findViewById(R.id.bk4);
        @SuppressLint("WrongViewCast") ImageButton book5 = (ImageButton)findViewById(R.id.bk5);
        @SuppressLint("WrongViewCast") ImageButton book6 = (ImageButton)findViewById(R.id.bk6);
        @SuppressLint("WrongViewCast") ImageButton home = (ImageButton)findViewById(R.id.imageButton16_hse);
        @SuppressLint("WrongViewCast") ImageButton lib = (ImageButton)findViewById(R.id.imageButton20);
        @SuppressLint("WrongViewCast") ImageButton noti = (ImageButton)findViewById(R.id.imageButton21);
        @SuppressLint("WrongViewCast") ImageButton user = (ImageButton)findViewById(R.id.imageButton22);

        top10books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t10 = new Intent(MainActivity.this,top10.class);
                startActivity(t10);
            }
        });

        latestbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lbooks = new Intent(MainActivity.this,latest.class);
                startActivity(lbooks);
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showrec = new Intent(MainActivity.this,showmore.class);
                startActivity(showrec);
            }
        });


        book1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bok1 = new Intent(MainActivity.this,bk1.class);
                startActivity(bok1);
            }
        });

        book2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bok2 = new Intent(MainActivity.this,bk2.class);
                startActivity(bok2);
            }
        });

        book3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bok3 = new Intent(MainActivity.this,bk3.class);
                startActivity(bok3);
            }
        });

        book4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bok4 = new Intent(MainActivity.this,bk4.class);
                startActivity(bok4);
            }
        });

        book5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bok5 = new Intent(MainActivity.this,bk5.class);
                startActivity(bok5);
            }
        });

        book6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bok6 = new Intent(MainActivity.this,bk6.class);
                startActivity(bok6);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hse = new Intent(MainActivity.this,h1.class);
                startActivity(hse);
            }
        });

        lib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent library = new Intent(MainActivity.this,l1.class);
                startActivity(library);
            }
        });

        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notifications = new Intent(MainActivity.this,n1.class);
                startActivity(notifications);
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent acct = new Intent(MainActivity.this,u1.class);
                startActivity(acct);
            }
        });

        /*top10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int1 = new Intent(MainActivity.this,top10.class);
                startActivity(int1);
            }
        });

        latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int2 = new Intent(MainActivity.this,latest.class);
                startActivity(int2);
            }
        });*/

        }

    }

