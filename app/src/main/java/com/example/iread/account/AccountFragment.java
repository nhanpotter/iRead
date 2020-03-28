package com.example.iread.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.iread.R;

public class AccountFragment extends Fragment {
    private TextView username;
    private TextView email;
    private Button logoutButton;

    public AccountFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_account, container, false);

        username = rootview.findViewById(R.id.username);
        email = rootview.findViewById(R.id.email);
        logoutButton = rootview.findViewById(R.id.logoutButton);

        return rootview;
    }
}
