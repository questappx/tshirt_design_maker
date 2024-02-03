package com.questappx.tshirtmaker.Extras;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

public class LongPressListener implements View.OnTouchListener {
    private static final long LONG_PRESS_DURATION = 30;
    private Handler handler;
    private Runnable longPressRunnable;
    private boolean isReleased;
    private OnLongPressListener longPressListener;

    public interface OnLongPressListener {
        void onLongPress();
    }

    public LongPressListener(OnLongPressListener listener) {
        this.longPressListener = listener;
        handler = new Handler();
        longPressRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isReleased && longPressListener != null) {
                    longPressListener.onLongPress();
                    handler.postDelayed(this, LONG_PRESS_DURATION);
                }
            }
        };
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isReleased = false;
                handler.postDelayed(longPressRunnable, LONG_PRESS_DURATION);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isReleased = true;
                handler.removeCallbacks(longPressRunnable);
                break;
        }
        return true;
    }
}
