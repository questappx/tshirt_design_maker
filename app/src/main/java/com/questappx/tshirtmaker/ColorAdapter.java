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


public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.RecyclerHolder>{

    Context context;
    int[] list;

    RecyclerListener listener;

    public ColorAdapter(Context context, int[] list, RecyclerListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.color_item, null, false);
        return new RecyclerHolder(view);
    }   

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, @SuppressLint("RecyclerView") int position) {


            holder.imageView.setImageResource(list[position]);



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

        ImageView imageView, imageviewSticker;
        TextView textFont;
        ImageView imageviewFilter;
        RelativeLayout proData;

        public RecyclerHolder(View view) {
            super(view);

            imageView = view.findViewById(R.id.imageview_frameitem);
            textFont = view.findViewById(R.id.textFont);
            imageviewFilter = view.findViewById(R.id.imageview_filteritem);
            imageviewSticker = view.findViewById(R.id.imageviewSticker);
            proData = view.findViewById(R.id.proDataLayout);
        }
    }



}
