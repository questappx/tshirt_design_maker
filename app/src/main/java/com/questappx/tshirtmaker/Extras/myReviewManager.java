package com.questappx.tshirtmaker.Extras;

import android.app.Activity;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;

public class myReviewManager {
    private Activity activity;
    private ReviewManager reviewManager;

    public myReviewManager(Activity activity) {
        this.activity = activity;
        this.reviewManager = ReviewManagerFactory.create(activity);
    }

//    public void checkIfUserRated() {
//        reviewManager.requestReviewFlow().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                ReviewInfo reviewInfo = task.getResult();
//                if (reviewInfo.getInlineReplySupported()) {
//                    // Show the rating dialog
//                }
//            }
//        });
//    }
}
