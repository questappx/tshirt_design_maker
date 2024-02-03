package com.questappx.tshirtmaker.AdsWorking;

import static com.questappx.tshirtmaker.Billing.InApp.isPaid;
import static com.questappx.tshirtmaker.EditorActivity.fbAdView;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.questappx.tshirtmaker.EditorActivity;
import com.questappx.tshirtmaker.R;

public class BannerAdImplement
{


    private static final String TAG = "BannerAdImplement";

    Context context;
    AdView adView;

    public BannerAdImplement(Context context, AdView adView) {
        this.context = context;
        this.adView = adView;

    }

    public void BannerAdLoad()
    {
        if(isPaid)
        {
            ((ViewGroup)adView.getParent()).setVisibility(View.GONE);
            return;
        }
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new com.google.android.gms.ads.AdListener(){

            @Override
            public void onAdLoaded() {
//                Toast.makeText( context, "Google Banner Loaded", Toast.LENGTH_SHORT).show();
                // Code to be executed when an ad finishes loading.
                adView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                adView.setVisibility(View.GONE);
                LinearLayout adContainer = ((Activity)context).findViewById(R.id.banner_container);
                new FacebookBannerAdLoader(context,fbAdView, adContainer);
                Log.i(TAG, "onAdFailedToLoad: "+ loadAdError.getMessage());
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }

}
