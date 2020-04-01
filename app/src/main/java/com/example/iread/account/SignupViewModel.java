package com.example.iread.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

class SignupViewModel extends ViewModel {
    private static final String LOG_TAG = SignupViewModel.class.getSimpleName();
    private final AccountRepository accountRepository;

    // Already Initialized
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<Boolean> mutableProgress = new MutableLiveData<>();
    public LiveData<Boolean> progress = mutableProgress;
    private MutableLiveData<SignUpResponse> mutableSignedUp = new MutableLiveData<>();
    public LiveData<SignUpResponse> signedUp = mutableSignedUp;
    private MutableLiveData<String> mutableError = new MutableLiveData<>();
    public LiveData<String> error = mutableError;

    @Inject
    public SignupViewModel(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void signUp(String username, String email, String password, String rePassword) {
        Single<SignUpResponse> signupObservable = accountRepository.signUp(username, email, password, rePassword);
        compositeDisposable.add(signupObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mutableProgress.setValue(true);
                })
                .doFinally(() -> {
                    mutableProgress.setValue(false);
                })
                .subscribe(data -> {
                    mutableSignedUp.setValue(data);
                }, error -> {
                    mutableError.setValue("Unable to signup");
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}