package com.qemasoft.alhabibshop.app.model;

/**
 * Created by Inzimam on 24-Nov-17.
 */

public class Reviews {

    private String reviewId;
    private String revAuthorName;
    private String revPostDate;
    private String reviewComment;
    private String authorRating;

    public Reviews(String revAuthorName, String revPostDate, String reviewComment,
                   String authorRating) {
        this.revAuthorName = revAuthorName;
        this.revPostDate = revPostDate;
        this.reviewComment = reviewComment;
        this.authorRating = authorRating;
    }

    public Reviews(String reviewId, String revAuthorName, String revPostDate,
                   String reviewComment, String authorRating) {
        this.reviewId = reviewId;
        this.revAuthorName = revAuthorName;
        this.revPostDate = revPostDate;
        this.reviewComment = reviewComment;
        this.authorRating = authorRating;
    }

    public String getReviewId() {
        return reviewId;
    }

    public String getRevAuthorName() {
        return revAuthorName;
    }

    public String getRevPostDate() {
        return revPostDate;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public String getAuthorRating() {
        return authorRating;
    }
}
