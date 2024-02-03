package com.questappx.tshirtmaker.SavedWorking;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by Toheed Mehmood on 12/14/2018.
 */

public class GetDisplayMatrix {
    Context context;
    LinearLayout.LayoutParams layoutParams;
    int newHeight = 0;
    private int findMyWidth;
    public GetDisplayMatrix(Context context) {
        this.context = context;
    }
    public void setImageDisplayMatrix(LinearLayout view)
    {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        findMyWidth = width/3;
//        int nHeight = findMyWidth/2;
            layoutParams = new LinearLayout.LayoutParams(findMyWidth,findMyWidth);
            view.setLayoutParams(layoutParams);



    }

}
