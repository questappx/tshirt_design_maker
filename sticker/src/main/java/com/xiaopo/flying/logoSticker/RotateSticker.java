package com.xiaopo.flying.logoSticker;

import android.view.MotionEvent;

import static com.xiaopo.flying.logoSticker.StickerView.handlingSticker;
import static com.xiaopo.flying.logoSticker.StickerView.stickers;

public class RotateSticker implements StickerIconEvent {
    @Override
    public void onActionDown(StickerView stickerView, MotionEvent event) {

    }

    @Override
    public void onActionMove(StickerView stickerView, MotionEvent event) {
        stickerView.rotateOnly(event);
    }

    @Override
    public void onActionUp(StickerView stickerView, MotionEvent event) {
//        stickers.remove(handlingSticker);
//        stickers.add(handlingSticker);
//        stickerView.invalidate();
        if (stickerView.getOnStickerOperationListener() != null) {
            stickerView.getOnStickerOperationListener()
                    .onStickerZoomFinished(stickerView.getCurrentSticker());
        }
    }
}
