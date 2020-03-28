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
        View rootview = inflater.inflate(R.layout.fragment_account, container, false);

        username = rootview.findViewById(R.id.username);
        email = rootview.findViewById(R.id.email);

        accountViewModel.getUserInfo();
        accountViewModel.userInfo.observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                username.setText(user.username);
                email.setText(user.email);
            }
        });

        logoutButton = rootview.findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutViewModel.logout();
                Navigation.findNavController(getView()).navigate(R.id.loginFragment);
            }
        });

        return rootview;
    }
}
