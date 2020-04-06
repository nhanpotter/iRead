package com.example.iread.account;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Singleton
public class AccountRepository {
    private static final String LOG_TAG = AccountRepository.class.getSimpleName();
    private final LoginService loginService;
    private final AuthService authService;
    private AuthHolder authHolder;

    @Inject
    public AccountRepository(LoginService loginService, AuthService authService, AuthHolder authHolder) {
        this.loginService = loginService;
        this.authService = authService;
        this.authHolder = authHolder;
    }

    public Single<LoginResponse> login(String username, String password) {
        return loginService.login(new Login(username, password))
                .map(it -> {
                    authHolder.setToken(it.authToken);
                    return it;
                });
    }

    public boolean isLoggedIn() {
        return authHolder.isLoggedIn();
    }

    public Single<User> getUserInfo() {
        return authService.getUserInfo();
    }

    public Completable logout() {
        return authService.logout().doOnComplete(() -> {
            authHolder.setToken(null);
        });
    }

    public Single<SignUpResponse> signUp(String username, String email, String password, String rePassword) {
        return loginService.signUp(new PostUser(username, email, password, rePassword));
    }

    public Completable resetPassword(String email) {
        return loginService.resetPassword(new Email(email));
    }
}
