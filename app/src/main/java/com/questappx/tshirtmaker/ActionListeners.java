package com.questappx.tshirtmaker;

import android.graphics.Bitmap;

public interface ActionListeners {
    void convertedWithError(String str);

    void convertedWithSuccess(Bitmap bitmap, String str);
}

