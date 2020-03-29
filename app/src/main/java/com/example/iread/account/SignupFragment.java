package com.example.iread.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.iread.R;
import com.example.iread.utils.CustomProgressDialog;

import javax.inject.Inject;

public class SignupFragment extends Fragment {
    public static final String LOG_TAG = SignupFragment.class.getSimpleName();
    private View rootView;
    @Inject
    SignupViewModel signupViewModel;

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//
//        ((MyApplication)MyApplication.applicationContext).appComponent.inject(this);
//    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_signup, container, false);

        final TextView usernameTextView = rootView.findViewById(R.id.user);
        final TextView emailTextView = rootView.findViewById(R.id.email);
        final TextView passwordTextview = rootView.findViewById(R.id.pw1);
        final TextView confirmPasswordTextview = rootView.findViewById(R.id.pw2);
        Button confirmSignup = rootView.findViewById(R.id.confirm);
        confirmSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordTextview.getText().toString().equals(confirmPasswordTextview.getText().toString())) {
                    String username = usernameTextView.getText().toString();
                    String email = emailTextView.getText().toString();
                    String password = passwordTextview.getText().toString();

                    Navigation.findNavController(rootView).navigate(R.id.loginFragment);
                }
            }
        });
        CustomProgressDialog dialog = new CustomProgressDialog(getContext());

        return rootView;
    }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
        }

        private void popBackStack() {
            int destinationId = R.id.homeFragment;
            Navigation.findNavController(rootView).popBackStack(destinationId, false);
        }
}
