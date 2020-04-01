package com.example.iread.account;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import com.example.iread.MyApplication;
import com.example.iread.R;
import com.example.iread.utils.CustomProgressDialog;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

public class AccountFragment extends Fragment {
    private TextView username;
    private TextView email;
    private Button logoutButton;

    @Inject
    AccountViewModel accountViewModel;
    @Inject
    LogoutViewModel logoutViewModel;

    public AccountFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MyApplication)MyApplication.applicationContext).appComponent.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        CustomProgressDialog dialog = new CustomProgressDialog(getContext());

        username = rootView.findViewById(R.id.username);
        email = rootView.findViewById(R.id.email);
        logoutButton = rootView.findViewById(R.id.logoutButton);

        accountViewModel.getUserInfo();
        accountViewModel.userInfo.observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                username.setText(user.username);
                email.setText(user.email);
            }
        });
        accountViewModel.error.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(rootView, s, Snackbar.LENGTH_LONG).show();
            }
        });

        accountViewModel.progress.observe(getViewLifecycleOwner(), new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean aBoolean) {
                dialog.show(aBoolean);
            }
        });
        
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutViewModel.logout();
            }
        });

        logoutViewModel.loggedIn.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean)
                    Navigation.findNavController(rootView).navigate(R.id.loginFragment);
            }
        });

        logoutViewModel.error.observe(getViewLifecycleOwner(), new Observer<String> () {
            @Override
            public void onChanged(String s) {
                Snackbar.make(rootView, s, Snackbar.LENGTH_LONG).show();
            }
        });

        logoutViewModel.progress.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                dialog.show(aBoolean);
            }
        });

        return rootView;
    }
}
