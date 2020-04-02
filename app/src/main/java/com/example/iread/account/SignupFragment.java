package com.example.iread.account;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import com.example.iread.MyApplication;
import com.example.iread.R;
import com.example.iread.utils.CustomProgressDialog;
import com.google.android.material.textfield.TextInputLayout;

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
        TextInputLayout usernameLayout = rootView.findViewById(R.id.user_wrapper);
        final TextView emailTextView = rootView.findViewById(R.id.email);
        TextInputLayout emailLayout = rootView.findViewById(R.id.email_wrapper);
        final TextView passwordTextview = rootView.findViewById(R.id.password);
        TextInputLayout passwordLayout = rootView.findViewById(R.id.password_wrapper);
        final TextView rePasswordTextview = rootView.findViewById(R.id.re_password);
        TextInputLayout rePasswordLayout = rootView.findViewById(R.id.re_password_wrapper);
        Button confirmSignup = rootView.findViewById(R.id.confirm);

        CustomProgressDialog dialog = new CustomProgressDialog(getContext());

        confirmSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameTextView.getText().toString();
                String email = emailTextView.getText().toString();
                String password = passwordTextview.getText().toString();
                String rePassword = rePasswordTextview.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    emailLayout.setError("This field may not be blank.");
                } else {
                    signupViewModel.signUp(username, email, password, rePassword);
                }
            }
        });

        signupViewModel.signedUp.observe(getViewLifecycleOwner(), new Observer<SignUpResponse>() {
            @Override
            public void onChanged(SignUpResponse signUpResponse) {
                Toast.makeText(getContext(), "An activation link had been sent to your email!",
                        Toast.LENGTH_LONG).show();
                Navigation.findNavController(rootView).navigate(R.id.loginFragment);
            }
        });

        signupViewModel.error.observe(getViewLifecycleOwner(), new Observer<SignUpErrorResponse>() {
            @Override
            public void onChanged(SignUpErrorResponse signUpErrorResponse) {
                if (signUpErrorResponse.username != null) {
                    usernameLayout.setError(String.join("\n", signUpErrorResponse.username));
                } else {
                    usernameLayout.setError(null);
                }
                if (signUpErrorResponse.email != null) {
                    emailLayout.setError(String.join("\n", signUpErrorResponse.email));
                } else {
                    emailLayout.setError(null);
                }
                if (signUpErrorResponse.password != null) {
                    passwordLayout.setError(String.join("\n", signUpErrorResponse.password));
                } else {
                    passwordLayout.setError(null);
                }
                if (signUpErrorResponse.rePassword != null) {
                    rePasswordLayout.setError(String.join("\n", signUpErrorResponse.rePassword));
                } else {
                    rePasswordLayout.setError(null);
                }
            }
        });

        signupViewModel.progress.observe(getViewLifecycleOwner(), new Observer<Boolean>() {

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
        }
}
