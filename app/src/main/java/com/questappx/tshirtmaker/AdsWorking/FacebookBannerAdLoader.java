package com.questappx.tshirtmaker.AdsWorking;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AdListener;
import com.questappx.tshirtmaker.R;

public class FacebookBannerAdLoader {
    private static final String TAG = "FacebookBannerAdLoader";
    private AdView adView;
    private Context context;
    private LinearLayout container;
    
    public FacebookBannerAdLoader(Context context, AdView adView, LinearLayout container) {
        this.context = context;
        this.adView = adView;
        this.container = container;

        loadBannerAd();
    }
    
    public void loadBannerAd() {
        container.setVisibility(View.VISIBLE);
        adView = new AdView(context, context.getResources().getString(R.string.fan_bannerId), AdSize.BANNER_HEIGHT_50);
        container.addView(adView);
        adView.loadAd();
    }
}
