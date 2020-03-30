package com.example.iread.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AccountViewModel extends ViewModel {
    private final AccountRepository accountRepository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<Boolean> mutableProgress = new MutableLiveData<>();
    public LiveData<Boolean> progress = mutableProgress;
    private MutableLiveData<User> mutableUserInfo = new MutableLiveData<>();
    public LiveData<User> userInfo = mutableUserInfo;
    private MutableLiveData<String> mutableError = new MutableLiveData<>();
    public LiveData<String> error = mutableError;

    @Inject
    public AccountViewModel(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void getUserInfo() {
        Single<User> userInfoObservable = accountRepository.getUserInfo();
        compositeDisposable.add(userInfoObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mutableProgress.setValue(true);
                })
                .doFinally(() -> {
                    mutableProgress.setValue(false);
                })
                .subscribe(data -> {
                    mutableUserInfo.setValue(data);
                }, error -> {
                    mutableError.setValue("Unable to get user info");
                    throw(error);
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
