package com.example.iread.account;

import android.content.Context;
import android.os.Bundle;
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

import com.example.iread.MyApplication;
import com.example.iread.R;
import com.example.iread.utils.CustomProgressDialog;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

public class SignupFragment extends Fragment {
    public static final String LOG_TAG = SignupFragment.class.getSimpleName();
    private View rootView;
    @Inject
    SignupViewModel signupViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        ((MyApplication) MyApplication.applicationContext).appComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_signup, container, false);

        final TextView usernameTextView = rootView.findViewById(R.id.user);
        final TextView emailTextView = rootView.findViewById(R.id.email);
        final TextView passwordTextview = rootView.findViewById(R.id.pw1);
        final TextView rePasswordTextview = rootView.findViewById(R.id.pw2);
        Button confirmSignup = rootView.findViewById(R.id.confirm);

        CustomProgressDialog dialog = new CustomProgressDialog(getContext());

        confirmSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordTextview.getText().toString().equals(rePasswordTextview.getText().toString())) {
                    String username = usernameTextView.getText().toString();
                    String email = emailTextView.getText().toString();
                    String password = passwordTextview.getText().toString();
                    String rePassword = rePasswordTextview.getText().toString();
                    signupViewModel.signUp(username, email, password, rePassword);

                    signupViewModel.signedUp.observe(getViewLifecycleOwner(), new Observer<SignUpResponse>() {
                        @Override
                        public void onChanged(SignUpResponse signUpResponse) {
                            Navigation.findNavController(rootView).navigate(R.id.loginFragment);
                        }
                    });

                    signupViewModel.error.observe(getViewLifecycleOwner(), new Observer<String> () {
                        @Override
                        public void onChanged(String s) {
                            Snackbar.make(rootView, s, Snackbar.LENGTH_LONG).show();
                        }
                    });

                    signupViewModel.progress.observe(getViewLifecycleOwner(), new Observer<Boolean>() {

                        @Override
                        public void onChanged(Boolean aBoolean) {
                            dialog.show(aBoolean);
                        }
                    });
                }
            }
        });

        return rootView;
    }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
        }
}
