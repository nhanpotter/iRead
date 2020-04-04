package com.example.iread.account;

import android.content.Context;
import android.os.Bundle;
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
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

public class ResetPasswordFragment extends Fragment {
    public static final String LOG_TAG = LoginFragment.class.getSimpleName();
    private View rootView;
    @Inject
    ResetPasswordViewModel resetPasswordViewModel;
    CustomProgressDialog dialog = new CustomProgressDialog(getContext());
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        ((MyApplication)MyApplication.applicationContext).appComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_resetpassword, container, false);

        final TextView emailTextView = rootView.findViewById(R.id.emailReset);
        Button confirmResetButton =  rootView.findViewById(R.id.confirmReset);
        confirmResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTextView.getText().toString();
                resetPasswordViewModel.resetPassword(email);
            }
        });

        resetPasswordViewModel.progress.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                dialog.show(aBoolean);
            }
        });

        resetPasswordViewModel.success.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Toast.makeText(getContext(), "A reset password link had been sent to your email", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(rootView).navigate(R.id.loginFragment);
                }
            }
        });

        resetPasswordViewModel.error.observe(getViewLifecycleOwner(), new Observer<ResetPasswordErrorResponse>() {
            @Override
            public void onChanged(ResetPasswordErrorResponse resetPasswordErrorResponse) {
                if (resetPasswordErrorResponse.email != null) {
                    emailResetLayout.setError(String.join("\n", resetPasswordErrorResponse.email));
                } else {
                    emailResetLayout.setError(null);
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
