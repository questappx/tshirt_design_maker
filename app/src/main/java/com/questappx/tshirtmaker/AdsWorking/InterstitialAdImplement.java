package com.questappx.tshirtmaker.AdsWorking;

import static com.questappx.tshirtmaker.Billing.InApp.isPaid;
import static com.questappx.tshirtmaker.MainActivity.fanInterstitialAd;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.questappx.tshirtmaker.MainActivity;
import com.questappx.tshirtmaker.R;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;

public class InterstitialAdImplement {

    private static final String TAG = "InterstitialAdImplement";
    Context context;

    long lastInterstitialShowTime = 0;
    boolean activityOpenAd = false;

    public InterstitialAdImplement(Context context) {
        this.context = context;
        if(isPaid)
        {
            return;
        }
        loadAdMobInterstitial();
//        loadFANInterstitial();
    }

    public void setActivityOpenAd(boolean bool) {
        activityOpenAd = bool;
    }

    public void loadAdMobInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context, context.getResources().getString(R.string.admob_interstitial), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        MainActivity.interstitialAd = interstitialAd;
                        Log.i(TAG, "AdMob Interstitial Loaded");

                        if (activityOpenAd) {
                            showInterstitial();
                            activityOpenAd = false;
                        }

                        MainActivity.interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                Log.d(TAG, "AdMob Interstitial Dismissed");
                                loadAdMobInterstitial();
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                MainActivity.interstitialAd = null;
                                Log.d(TAG, "AdMob Interstitial Shown");
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.i(TAG, "AdMob Interstitial Failed to Load: " + loadAdError.getMessage());
                        MainActivity.interstitialAd = null;
                        loadFANInterstitial();
                    }
                });
    }


    public void loadFANInterstitial() {
        // Initialize and load Facebook Audience Network interstitial ad
        InterstitialAdListener fanInterstitialListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                Log.d(TAG, "FAN Interstitial Shown");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                Log.d(TAG, "FAN Interstitial Dismissed");
                loadFANInterstitial();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.i(TAG, "FAN Interstitial Failed to Load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Log.i(TAG, "FAN Interstitial Loaded");
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.d(TAG, "FAN Interstitial Clicked");
            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };

        fanInterstitialAd = new com.facebook.ads.InterstitialAd(context, context.getResources().getString(R.string.fan_interstitial));
        fanInterstitialAd.loadAd(
                fanInterstitialAd.buildLoadAdConfig()
                        .withAdListener(fanInterstitialListener)
                        .build()
        );
    }
    public void showInterstitial() {
        if(isPaid)
        {
            return;
        }
        long currentTime = System.currentTimeMillis();
        long elapsedTimeSinceLastShow = currentTime - lastInterstitialShowTime;
        long oneMinuteInMillis = 60 * 1000;

        if (MainActivity.interstitialAd != null && elapsedTimeSinceLastShow >= oneMinuteInMillis) {
            MainActivity.interstitialAd.show(((Activity) context));
        } else if (fanInterstitialAd != null && fanInterstitialAd.isAdLoaded() && elapsedTimeSinceLastShow >= oneMinuteInMillis) {
            fanInterstitialAd.show();
        }
    }
}
