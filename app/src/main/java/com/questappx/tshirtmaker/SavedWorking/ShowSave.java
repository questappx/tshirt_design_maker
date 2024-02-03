package com.questappx.tshirtmaker.SavedWorking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.questappx.tshirtmaker.R;


public class ShowSave extends AppCompatActivity{


    TextView del,share;
    ImageView imageView;
    private final String TAG = ShowSave.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_save);



        imageView=(ImageView)findViewById(R.id.img);

//
        Glide.with(this).load(SaveWork.files.get(SaveWork.pos)).into(imageView);

    }

    public void moreApps(View view)
    {
        Uri uri = Uri.parse("https://play.google.com/store/apps/developer?id=Quest%20Appx");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void Share(View view)
    {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Download App and Design more tshirts : "+"https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName());
        Uri uri = FileProvider.getUriForFile(ShowSave.this, getApplicationContext().getPackageName() + ".provider", SaveWork.files.get(SaveWork.pos));
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/png");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "send"));
    }

    public void Delete(View view)
    {
        new DeleteAlert(view.getContext()).textDialog();
    }



}
