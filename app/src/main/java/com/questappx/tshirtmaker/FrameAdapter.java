package com.questappx.tshirtmaker;

import static com.questappx.tshirtmaker.Billing.InApp.isPaid;
import static com.questappx.tshirtmaker.Data.SHIRT_ADAPTER;
import static com.questappx.tshirtmaker.Data.bgShirtLink;
import static com.questappx.tshirtmaker.Data.shirtLink;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.ar.core.Frame;

import java.util.List;


public class FrameAdapter extends RecyclerView.Adapter<FrameAdapter.RecyclerHolder>{
    Context context;
    List<String> list;
     int method;
    FrameListener listener;
    ProDataListener proDataListener;



    public FrameAdapter(Context context, List<String> list, int method, FrameListener listener) {
        this.context = context;
        this.list = list;
        this.method = method;
        this.listener = listener;
    }

//    @NonNull
//    @Override

    @NonNull
    @Override
    public FrameAdapter.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = LayoutInflater.from(context).inflate(R.layout.bg_item, null, false);
        FrameAdapter.RecyclerHolder viewHolder = new FrameAdapter.RecyclerHolder(view);
        return viewHolder;
    }

    public void setListener(FrameListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull FrameAdapter.RecyclerHolder holder1, @SuppressLint("RecyclerView") int position) {

            Glide.with(context).load(list.get(position)).placeholder(R.drawable.loading_animation).into(holder1.imageView);



            holder1.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnClick(position);
                }
            });

            holder1.proLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    proDataListener.onClick(position);
                }
            });

            if(method == SHIRT_ADAPTER)
            {
                if(isPaid)
                {
                    holder1.proLayout.setVisibility(View.GONE);
                }
                else if(position >= (list.size()/1.7))
                {
                    holder1.proLayout.setVisibility(View.VISIBLE);
                }
                else
                {
                    holder1.proLayout.setVisibility(View.GONE);
                }
            }
            else {
                holder1.proLayout.setVisibility(View.GONE);
            }

    }



    public void setProDataListener(ProDataListener listener)
    {
        this.proDataListener = listener;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        RelativeLayout proLayout;

        public RecyclerHolder(View view) {
            super(view);

            imageView = view.findViewById(R.id.imageview_frameitem);
            proLayout = view.findViewById(R.id.proDataLayout);
        }
    }



}
