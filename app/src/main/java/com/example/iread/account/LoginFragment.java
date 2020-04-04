package com.example.iread.account;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import com.example.iread.MainActivity;
import com.example.iread.MyApplication;
import com.example.iread.R;
import com.example.iread.utils.CustomProgressDialog;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

public class LoginFragment extends Fragment {
    public static final String LOG_TAG = LoginFragment.class.getSimpleName();
    private View rootView;
    @Inject
    LoginViewModel loginViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        ((MyApplication)MyApplication.applicationContext).appComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);

        final TextView usernameTextView = (TextView) rootView.findViewById(R.id.username_field);
        final TextView passwordTextview = (TextView) rootView.findViewById(R.id.password_field);
        Button button = (Button) rootView.findViewById(R.id.login_button);
        TextView signupTextView = rootView.findViewById(R.id.signup);
        TextView resetPasswordTextView = rootView.findViewById(R.id.resetPassword);
        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(rootView).navigate(R.id.signupFragment);
            }
        });
        resetPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(rootView).navigate(R.id.resetPasswordFragment);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameTextView.getText().toString();
                String password = passwordTextview.getText().toString();
                loginViewModel.login(username, password);
            }
        });
        CustomProgressDialog dialog = new CustomProgressDialog(getContext());

        loginViewModel.loggedIn.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                    Navigation.findNavController(rootView).navigate(R.id.homeFragment);
            }
        });

        loginViewModel.error.observe(getViewLifecycleOwner(), new Observer<String> () {
            @Override
            public void onChanged(String s) {
                Snackbar.make(rootView, s, Snackbar.LENGTH_LONG).show();
                Toast.makeText(getContext(), "Please check your email account " +
                        "for activation link if you already signed up!", Toast.LENGTH_LONG).show();
            }
        });

        loginViewModel.progress.observe(getViewLifecycleOwner(), new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean aBoolean) {
                dialog.show(aBoolean);
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (loginViewModel.isLoggedIn()) {
            Navigation.findNavController(rootView).navigate(R.id.homeFragment);
        }
    }

    private void popBackStack() {
        int destinationId = R.id.homeFragment;
        Navigation.findNavController(rootView).popBackStack(destinationId, false);
    }

}
