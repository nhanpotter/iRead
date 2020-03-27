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
    private AuthHolder authHolder;

    @Inject
    public AccountRepository(LoginService loginService, AuthHolder authHolder) {
        this.loginService = loginService;
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
        return loginService.getUserInfo();
    }

    public Completable logout() {
        return loginService.logout().doOnComplete(() -> {
            authHolder.setToken(null);
        });
    }
}
