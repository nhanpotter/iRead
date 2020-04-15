package com.example.iread;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.iread.di.ApplicationComponent;
import com.example.iread.home.HomeFragment;
import com.example.iread.utils.NetworkUtils;
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

        // Check internet connection
        NetworkUtils networkUtils = new NetworkUtils();
        if (!networkUtils.isNetworkConnected()) {
            showNoInternetDialog(MainActivity.this);
        }

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onLost(@NonNull Network network) {
                List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
                try {
                    Context context = MainActivity.this;
                    for (Fragment fragment : fragmentList) {
                        Context fragContext = fragment.getContext();
                        if (fragContext != null) {
                            context = fragContext;
                            break;
                        }
                    }
                    showNoInternetDialog(context);
                } catch (Exception e) {
                    finishAndRemoveTask();
                }
            }
        };

        NetworkRequest networkRequest = new NetworkRequest.Builder().build();

        cm.registerNetworkCallback(networkRequest, networkCallback);
    }
    @Override
    public void onBackPressed() {
        Fragment hostFragment = getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        if (hostFragment instanceof HomeFragment) finish();
        else super.onBackPressed();
    }

    public void showNoInternetDialog(Context context) {
        new AlertDialog.Builder(context, R.style.Theme_AppCompat_Dialog)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage("No network connectivity")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finishAndRemoveTask();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
