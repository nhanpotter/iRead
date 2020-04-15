package com.example.iread;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.iread.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        Fragment hostFragment = getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        navController = ((NavHostFragment) hostFragment).getNavController();

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                @NonNull NavDestination destination, @Nullable Bundle arguments) {
                List<Integer> screenWithoutNavBar = Arrays.asList(
                        R.id.loginFragment, R.id.signupFragment, R.id.resetPasswordFragment
                );
                if (screenWithoutNavBar.contains(destination.getId())) {
                    bottomNavigationView.setVisibility(View.GONE);
                } else {
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }
    @Override
    public void onBackPressed() {
        Fragment hostFragment = getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        if (hostFragment instanceof HomeFragment) finish();
        else super.onBackPressed();
    }
}
