package com.example.iread.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LogoutViewModel extends ViewModel {
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
    public LogoutViewModel(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean isLoggedIn() {
        return accountRepository.isLoggedIn();
    }

    public void logout() {
        Completable logoutObservable = accountRepository.logout();
        compositeDisposable.add(logoutObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mutableProgress.setValue(true);
                })
                .doFinally(() -> {
                    mutableProgress.setValue(false);
                })
                .subscribe(() -> {
                    mutableLoggedIn.setValue(false);
                }, error -> {
                    mutableError.setValue("Unable to logout");
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
