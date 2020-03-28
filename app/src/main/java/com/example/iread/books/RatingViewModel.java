package com.example.iread.books;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RatingViewModel extends ViewModel {
    private final FeedbackRepository feedbackRepository;

    @Inject
    public RatingViewModel(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    // Initialized
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<Boolean> mutableProgress = new MutableLiveData<>();
    public LiveData<Boolean> progress = mutableProgress;
    private MutableLiveData<Rating> mutableRatingTotal = new MutableLiveData<>();
    public LiveData<Rating> ratingTotal = mutableRatingTotal;
    private MutableLiveData<Rating> mutableRating = new MutableLiveData<>();
    public LiveData<Rating> rating = mutableRating;
    private MutableLiveData<String> mutableError = new MutableLiveData<>();
    public LiveData<String> error = mutableError;

    public void getRating(int id) {
        Single<Rating> ratingTotalObservable = feedbackRepository.getRating(id);
        compositeDisposable.add(ratingTotalObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mutableProgress.setValue(true);
                })
                .doFinally(() -> {
                    mutableProgress.setValue(false);
                })
                .subscribe(data -> {
                    mutableRatingTotal.setValue(data);
                }, error -> {
                    mutableError.setValue("Unable to get rating");
                    throw(error);
                })
        );
    }

    public void postRating(int id, float rating) {
        Single<Rating> ratingObservable = feedbackRepository.postRating(id, rating);
        compositeDisposable.add(ratingObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mutableProgress.setValue(true);
                })
                .doFinally(() -> {
                    mutableProgress.setValue(false);
                })
                .subscribe(data -> {
                    mutableRating.setValue(data);
                }, error -> {
                    mutableError.setValue("Unable to post rating");
                    throw(error);
                })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
