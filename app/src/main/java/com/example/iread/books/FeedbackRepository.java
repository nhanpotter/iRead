package com.example.iread.books;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;

@Singleton
public class FeedbackRepository {
    FeedbackService feedbackService;
    @Inject
    public FeedbackRepository(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    public Single<List<Comment>> getCommentList(int bookId) {
        return feedbackService.getCommentList(bookId);
    }

    public Single<Comment> postComment(int bookId, String comment) {
        return feedbackService.postComment(bookId, new PostComment(comment));
    }

    public Single<Rating> getRating(int bookId) {
        return feedbackService.getRating(bookId);
    }

    public Single<Rating> postRating(int bookId, int rating) {
        return feedbackService.postRating(bookId, new PostRating(rating));
    }
}
