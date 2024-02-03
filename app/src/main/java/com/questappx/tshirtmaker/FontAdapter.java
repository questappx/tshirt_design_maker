package com.questappx.tshirtmaker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


public class FontAdapter extends RecyclerView.Adapter<FontAdapter.RecyclerHolder>{

    Context context;
    int[] list;

    RecyclerListener listener;

    public FontAdapter(Context context, int[] list, RecyclerListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.font_item, null, false);
        return new RecyclerHolder(view);
    }   

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, @SuppressLint("RecyclerView") int position) {



            Typeface typeface = ResourcesCompat.getFont(context, list[position]);
            holder.textFont.setTypeface(typeface);


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnClick(position);
            }
        });

    }



    @Override
    public int getItemCount() {
        return list.length;
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {

        TextView textFont;
        ImageView imageView;

        public RecyclerHolder(View view) {
            super(view);

            imageView = view.findViewById(R.id.imageview_frameitem);
            textFont = view.findViewById(R.id.textFont);
        }
    }



}
