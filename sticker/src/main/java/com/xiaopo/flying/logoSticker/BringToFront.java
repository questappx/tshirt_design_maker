package com.xiaopo.flying.logoSticker;

import android.view.MotionEvent;

import static com.xiaopo.flying.logoSticker.StickerView.handlingSticker;
import static com.xiaopo.flying.logoSticker.StickerView.stickers;

public class BringToFront implements StickerIconEvent {
    @Override
    public void onActionDown(StickerView stickerView, MotionEvent event) {

    }

    @Override
    public void onActionMove(StickerView stickerView, MotionEvent event) {

    }

    @Override
    public void onActionUp(StickerView stickerView, MotionEvent event) {
        stickers.remove(handlingSticker);
        stickers.add(handlingSticker);
        stickerView.invalidate();
    }
}
