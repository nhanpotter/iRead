package com.example.iread.account;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import com.example.iread.MainActivity;
import com.example.iread.MyApplication;
import com.example.iread.R;

import javax.inject.Inject;

public class LoginFragment extends Fragment {
    public static final String LOG_TAG = LoginFragment.class.getSimpleName();
    private View rootView;
    @Inject
    LoginViewModel loginViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        MainActivity mainActivity = (MainActivity) getActivity();
        ((MyApplication)context.getApplicationContext()).appComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);

        final TextView usernameTextView = (TextView) rootView.findViewById(R.id.username_field);
        final TextView passwordTextview = (TextView) rootView.findViewById(R.id.password_field);
        Button button = (Button) rootView.findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameTextView.getText().toString();
                String password = passwordTextview.getText().toString();
                loginViewModel.login(username, password);
            }
        });
        Log.d(LOG_TAG, rootView.toString());
        if (loginViewModel.isLoggedIn()) {
            Log.d(LOG_TAG, rootView.toString());
            Navigation.findNavController(rootView).navigate(R.id.homeFragment);
        }

        loginViewModel.loggedIn.observe(getViewLifecycleOwner(), new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                    Navigation.findNavController(rootView).navigate(R.id.homeFragment);
            }
        });

        return rootView;
    }

    private void popBackStack() {
        int destinationId = R.id.homeFragment;
        Navigation.findNavController(rootView).popBackStack(destinationId, false);
    }
}
