package com.example.iread.account;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {
    private static final String LOG_TAG = LoginViewModel.class.getSimpleName();
    private final AccountRepository accountRepository;

    // Already Initialized
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<Boolean> mutableProgress = new MutableLiveData<>();
    public LiveData<Boolean> progress = mutableProgress;
    private MutableLiveData<Boolean> mutableLoggedIn = new MutableLiveData<>();
    public LiveData<Boolean> loggedIn = mutableLoggedIn;
    private MutableLiveData<String> mutableError = new MutableLiveData<>();
    public LiveData<String> error = mutableError;

    @Inject
    public LoginViewModel(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean isLoggedIn() {
        return accountRepository.isLoggedIn();
    }

    public void login(String username, String password) {
        Single<LoginResponse> loginObservable = accountRepository.login(username, password);
        compositeDisposable.add(loginObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mutableProgress.setValue(true);
                })
                .doFinally(() -> {
                    mutableProgress.setValue(false);
                })
                .subscribe(data -> {
                    mutableLoggedIn.setValue(true);
                }, error -> {
                    mutableError.setValue("Unable to login");
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
