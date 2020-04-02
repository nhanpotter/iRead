package com.example.iread.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;

public class ResetPasswordViewModel extends ViewModel {
    private static final String LOG_TAG = ResetPasswordViewModel.class.getSimpleName();
    private final AccountRepository accountRepository;

    // Already Initialized
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<Boolean> mutableProgress = new MutableLiveData<>();
    public LiveData<Boolean> progress = mutableProgress;
    private MutableLiveData<Boolean> mutableSuccess = new MutableLiveData<>(false);
    public LiveData<Boolean> success = mutableSuccess;
    private MutableLiveData<ResetPasswordErrorResponse> mutableError = new MutableLiveData<>();
    public LiveData<ResetPasswordErrorResponse> error = mutableError;

    @Inject
    public ResetPasswordViewModel(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void resetPassword(String email) {
        Completable resetPasswordObservable = accountRepository.resetPassword(email);
        compositeDisposable.add(resetPasswordObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mutableProgress.setValue(true);
                })
                .doFinally(() -> {
                    mutableProgress.setValue(false);
                })
                .subscribe(() -> {
                    mutableSuccess.setValue(true);
                }, error -> {
                    ResetPasswordErrorResponse resetPasswordErrorResponse;
                    String errorBody = ((HttpException) error).response().errorBody().string();
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        resetPasswordErrorResponse = mapper
                                .readValue(errorBody, ResetPasswordErrorResponse.class);
                    } catch (Exception e) {
                        List<String> list = new ArrayList<>();
                        list.add(errorBody);
                        resetPasswordErrorResponse = new ResetPasswordErrorResponse();
                        resetPasswordErrorResponse.email = list;
                    }
                    mutableError.setValue(resetPasswordErrorResponse);
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
