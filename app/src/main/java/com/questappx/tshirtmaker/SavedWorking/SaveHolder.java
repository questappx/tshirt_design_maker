package com.questappx.tshirtmaker.SavedWorking;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.questappx.tshirtmaker.R;


public class SaveHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView imageView;
    LinearLayout linearLayout;
    public SaveHolder(View itemView) {
        super(itemView);
        imageView=(ImageView)itemView.findViewById(R.id.saveLoadImage);
        linearLayout = (LinearLayout)itemView.findViewById(R.id.savedItemsLayout);
        new GetDisplayMatrix(itemView.getContext()).setImageDisplayMatrix(linearLayout);
        imageView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view==imageView)
        {
            SaveWork.pos = getAdapterPosition();
            view.getContext().startActivity(new Intent(view.getContext(), ShowSave.class));

        }
    }
}
