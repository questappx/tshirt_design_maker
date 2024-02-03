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
import com.questappx.tshirtmaker.R;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder>{

    Context context;
    int[] list;
     int method;
    RecyclerListener listener;
    ProDataListener proDataListener;

    //Method 1 == frames
    //Method 2 == Colors
    //Method 3 == Fonts
    //Method 4 == Filter
    //Method 5 == Stickers
    //Method 10 == Other Apps Pro Frames

    public RecyclerAdapter(Context context, int[] list, int method, RecyclerListener listener) {
        this.context = context;
        this.list = list;
        this.method = method;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.griditem_frames, null, false);
        return new RecyclerHolder(view);
    }   

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, @SuppressLint("RecyclerView") int position) {

        //Frame
        if(method == 1)
        {
//            holder.imageView.setImageResource(list[position]);
            Glide.with(context).load(list[position]).thumbnail(0.5f).into(holder.imageView);

            if(isProContent(list[position]))
            {
                holder.proData.setVisibility(View.VISIBLE);
            }


            else
            {
                if(holder.proData.getVisibility() == View.VISIBLE)
                {
                    holder.proData.setVisibility(View.GONE);
                }
            }

            //set this as visible as proData
//            if(proDataListener.setVisible(list[position]))
//            {
//                holder.proData.setVisibility(View.VISIBLE);
//
                holder.proData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        proDataListener.onClick(position);
                    }
                });
//            }
        }
        //Colors
        else if(method == 2)
        {

//            holder.imageView.setBackgroundResource(list[position]);
            holder.imageView.setImageResource(list[position]);

        }
        //Colors for shirts
        else if(method == 22)
        {
            if (position == 0)
            {
                holder.imageView.setImageResource(list[position]);
            }
            else if(position == 1)
            {
                holder.imageView.setImageResource(R.drawable.cross_circle);
            }
            else
            {
                holder.imageView.setImageResource(list[position]);
            }
//            holder.imageView.setBackgroundResource(list[position]);

        }

        //Fonts
        else if(method == 3)
        {
            holder.textFont.setText("Font");
            Typeface typeface = ResourcesCompat.getFont(context, list[position]);
            holder.textFont.setTypeface(typeface);
        }

        //Filters
        else if(method == 4)
        {
            if(position == 0)
            {
                holder.imageView.setImageResource(R.drawable.cross_circle);
                holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            else
            {
//                holder.imageView.setImageResource(list[position]);
                Glide.with(context).load(list[position]).thumbnail(0.5f).into(holder.imageView);
            }



//            if(!(holder.getAdapterPosition() == 0))
//                holder.imageviewFilter.setImageResource(list[position]);
        }

        //Stickers
        else if(method == 5)
        {
            //setting black background
            holder.imageView.setBackgroundResource(R.color.lightgrey);
//            holder.imageviewSticker.setImageResource(list[position]);
            Glide.with(context).load(list[position]).into(holder.imageviewSticker);
        }


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnClick(position);
            }
        });

    }

    private boolean isProContent(int drawable) {
//        int[] proDrawables = {R.drawable.tshirt20, R.drawable.tshirt38,R.drawable.tshirt120,R.drawable.tshirt55,R.drawable.tshirt65,R.drawable.tshirt83,R.drawable.tshirt89,R.drawable.tshirt115,R.drawable.tshirt138,R.drawable.tshirt164,R.drawable.tshirt166,R.drawable.tshirt235,R.drawable.tshirt228,R.drawable.tshirt168,R.drawable.tshirt104,R.drawable.tshirt37,R.drawable.tshirt69,R.drawable.tshirt130,R.drawable.tshirt191,R.drawable.tshirt192,R.drawable.tshirt197,R.drawable.tshirt202,R.drawable.tshirt203, };
//
//        for(int i=0;i<proDrawables.length;i++)
//        {
//            if(drawable == proDrawables[i])
//            {
//                return true;
//            }
//        }

        return false;
    }

    public void setProDataListener(ProDataListener listener)
    {
        this.proDataListener = listener;
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
