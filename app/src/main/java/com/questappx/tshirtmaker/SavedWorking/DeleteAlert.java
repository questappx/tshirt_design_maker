package com.questappx.tshirtmaker.SavedWorking;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.questappx.tshirtmaker.R;

public class DeleteAlert {
    Context context;

    public DeleteAlert(Context context) {
        this.context = context;
    }

    public void textDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.delete_alert);
        TextView deleteNo = (TextView) dialog.findViewById(R.id.deleteNo);
        TextView deleteYes = (TextView) dialog.findViewById(R.id.deleteYes);


        deleteNo.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        deleteYes.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
                SaveWork.files.get(SaveWork.pos).delete();
                SaveWork.files.remove(SaveWork.pos);
                ((Activity)context).finish();

            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }



}
