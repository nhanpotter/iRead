package com.example.iread.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.iread.R;

public class BookDetailsActivity extends AppCompatActivity {
    Button AddRatingBtn;
    RatingBar ratingBarStars;
    float userRating = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        AddRatingBtn = findViewById(R.id.AddRateButton);
        ratingBarStars = findViewById(R.id.ratingBar);

        ratingBarStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                int rating = (int) v;
                String message = null;

                userRating = ratingBar.getRating();

                switch (rating) {
                    case 1:
                        message = "Thank you for rating the book!";
                        break;
                    case 2:
                        message = "Thank you for rating the book!";
                        break;
                    case 3:
                        message = "Thank you for rating the book!";
                        break;
                    case 4:
                        message = "Thank you for rating the book!";
                        break;
                    case 5:
                        message = "Thank you for rating the book!";
                        break;
                }
                Toast.makeText(BookDetailsActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
        AddRatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BookDetailsActivity.this, String.valueOf(userRating), Toast.LENGTH_LONG).show();
            }
        });
    }
}