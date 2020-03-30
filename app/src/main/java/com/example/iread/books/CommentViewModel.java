package com.example.iread.books;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CommentViewModel extends ViewModel {
    private final FeedbackRepository feedbackRepository;

    @Inject
    public CommentViewModel(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    // Initialized
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<Boolean> mutableProgress = new MutableLiveData<>();
    public LiveData<Boolean> progress = mutableProgress;
    private MutableLiveData<List<Comment>> mutableCommentList = new MutableLiveData<>();
    public LiveData<List<Comment>> commentList = mutableCommentList;
    private MutableLiveData<Comment> mutableComment = new MutableLiveData<>();
    public LiveData<Comment> comment = mutableComment;
    private MutableLiveData<String> mutableError = new MutableLiveData<>();
    public LiveData<String> error = mutableError;

    public void getCommentList(int id) {
        Single<List<Comment>> commentListObservable = feedbackRepository.getCommentList(id);
        compositeDisposable.add(commentListObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mutableProgress.setValue(true);
                })
                .doFinally(() -> {
                    mutableProgress.setValue(false);
                })
                .subscribe(data -> {
                    mutableCommentList.setValue(data);
                }, error -> {
                    mutableError.setValue("Unable to get rating");
                    throw(error);
                })
        );
    }

    public void postComment(int id, String comment) {
        Single<Comment> commentObservable = feedbackRepository.postComment(id, comment);
        compositeDisposable.add(commentObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mutableProgress.setValue(true);
                })
                .doFinally(() -> {
                    mutableProgress.setValue(false);
                })
                .subscribe(data -> {
                    mutableComment.setValue(data);
                }, error -> {
                    mutableError.setValue("Unable to get rating");
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
