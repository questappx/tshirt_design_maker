package com.questappx.tshirtmaker;

import static com.questappx.tshirtmaker.Billing.InApp.isPaid;

import static java.lang.System.exit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
import com.questappx.tshirtmaker.AdsWorking.AppOpenManager;
import com.questappx.tshirtmaker.AdsWorking.Gdpr;
import com.questappx.tshirtmaker.AdsWorking.InterstitialAdImplement;
import com.questappx.tshirtmaker.Billing.InApp;
import com.questappx.tshirtmaker.Extras.Utility;
import com.questappx.tshirtmaker.SavedWorking.SaveWork;


public class MainActivity extends AppCompatActivity {
    RelativeLayout splashLayout;
    ImageButton createFramesBtn, savedImages, moreApp, rateus;
    TextView disclaimerBtn,privacyPolicy, purchaseStatus;
    LinearLayout goPremiumLayout;

    public static InApp inApp;



    public static InterstitialAdImplement interstitialAdImplement;
    public static com.facebook.ads.InterstitialAd fanInterstitialAd;

    public static com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd;

    public static AppOpenManager appOpenManager;


    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_main);

        Gdpr gdpr = new Gdpr(this);
        gdpr.setGdpr();

        SplashWorking();

        linkXml();
        clicklisteners();




    }



    void askRatings() {
        ReviewManager manager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Task is succesfully", Toast.LENGTH_SHORT).show();
                // We can get the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();
                Task<Void> flow = manager.launchReviewFlow(this, reviewInfo);
                flow.addOnCompleteListener(task2 -> {
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                });
            } else {
                Toast.makeText(this, "Task is succesfully", Toast.LENGTH_SHORT).show();
                // There was some problem, continue regardless of the result.
            }
        });
    }



    private void linkXml() {

        inApp = new InApp(this);
        splashLayout = findViewById(R.id.splashScreen);

        createFramesBtn = findViewById(R.id.btn_create);
        savedImages = findViewById(R.id.savedImages);
        moreApp = findViewById(R.id.btn_moreapp);
        disclaimerBtn = findViewById(R.id.tv_Disclaimer);
        rateus = findViewById(R.id.rate_us);
        privacyPolicy = findViewById(R.id.tv_privacyPolicy);
        goPremiumLayout = findViewById(R.id.goPremiumLayout);
        purchaseStatus = findViewById(R.id.purchaseStatus);

        if(isPaid)
        {
            goPremiumLayout.setVisibility(View.GONE);
            purchaseStatus.setText("(Subscribed)");
        }
//        else {
//            goPremiumLayout.setVisibility(View.VISIBLE);
//        }

    }

    private void clicklisteners() {
        createFramesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                func_rateus();
                askRatings();
//                RateApp(MainActivity.this);
            }
        });


        goPremiumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPaid)
                {
                    Toast.makeText(MainActivity.this, "Already using Premium Version", Toast.LENGTH_SHORT).show();
                    return;
                }
                inApp.showDialog(MainActivity.this);
            }
        });


//        savedImages.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                func_shareapp();
//            }
//        });

        moreApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func_moreapps();
            }
        });

        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func_privacypolicy();;
            }
        });


        disclaimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDisclaimerDialog();
            }
        });

    }



    private void showDisclaimerDialog() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.disclaimer);
        dialog.show();
        Button buttonOk = dialog.findViewById(R.id.dialogBtnOk);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void func_privacypolicy()
    {
        Uri uri = Uri.parse(Data.PrivacyPolicy);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void func_rateus()
    {
        String appLink = "https://play.google.com/store/apps/details?id=" + getPackageName();
        Uri uri = Uri.parse(appLink);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    private void func_shareapp()
    {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
            String shareMessage= "\nLet your Friends know that you are using this app... Install now from link below:\n\n";
            String appLink = "https://play.google.com/store/apps/details?id=" + getPackageName();
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage+appLink);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void func_moreapps()
    {
        Uri uri = Uri.parse("https://play.google.com/store/apps/developer?id=Quest%20Appx");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

//    private void checkInternetConnection() {
//
//        if (internet.isConnected()) {
//            new isInternetActive().execute();
//        } else {
//            Toast.makeText(this, "Please check your internet", Toast.LENGTH_SHORT).show();
//        }
//
//    }

    private void checkPermissions()
    {
        Utility utility = new Utility();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Utility.checkPermissionContects(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {


                    if (Utility.checkPermissionContects(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    }
                }
            }
        }
    }

    public void saveWork(View view) {
        startActivity(new Intent(MainActivity.this, SaveWork.class));
    }


    private void SplashWorking() {
        appOpenManager = new AppOpenManager(this);
        appOpenManager.method = 1;


        interstitialAdImplement = new InterstitialAdImplement(this);


        splashLayout = findViewById(R.id.splashScreen);
        splashLayout.setVisibility(View.VISIBLE);
//        ProgressBar progressBar = findViewById(R.id.splashProgressBar);
//        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.MULTIPLY);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                splashLayout.setVisibility(View.GONE);
                checkPermissions();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        appOpenManager.method = 0;
                    }
                },1000);

            }
        }, 4500);
    }

    private void rateusDialog() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.rateus_dialog);
        dialog.show();
        ImageView buttonCancel = dialog.findViewById(R.id.dialogBtnCancel);
        ImageView buttonNoThanks = dialog.findViewById(R.id.dialogNoThanks);
        ImageView buttonOk = dialog.findViewById(R.id.dialogBtnSubmit);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCancelable(false);



        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func_rateus();
                dialog.dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        buttonNoThanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        rateusDialog();
    }
}
