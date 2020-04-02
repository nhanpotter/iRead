package com.example.iread.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ResetPasswordViewModel extends ViewModel {
    private static final String LOG_TAG = ResetPasswordViewModel.class.getSimpleName();
    private final AccountRepository accountRepository;

    // Already Initialized
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<Boolean> mutableProgress = new MutableLiveData<>();
    public LiveData<Boolean> progress = mutableProgress;
    private MutableLiveData<Email> mutableResetEmail = new MutableLiveData<>();
    public LiveData<Email> resetEmail = mutableResetEmail;
    private MutableLiveData<String> mutableError = new MutableLiveData<>();
    public LiveData<String> error = mutableError;

    @Inject
    public ResetPasswordViewModel(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void resetPassword(String email) {
        Single<Email> resetPasswordObservable = accountRepository.resetPassword(email);
        compositeDisposable.add(resetPasswordObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mutableProgress.setValue(true);
                })
                .doFinally(() -> {
                    mutableProgress.setValue(false);
                })
                .subscribe(data -> {
                    mutableResetEmail.setValue(data);
                }, error -> {
                    mutableError.setValue("Unable to reset password");
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
