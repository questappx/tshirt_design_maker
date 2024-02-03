package com.questappx.tshirtmaker.SavedWorking;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.questappx.tshirtmaker.R;

import java.io.File;
import java.util.ArrayList;


public class SaveAdapter extends RecyclerView.Adapter<SaveHolder> {
    ArrayList<File> files;
    Context context;

    public SaveAdapter(ArrayList<File> files, Context context) {
        this.files = files;
        this.context = context;
    }

    @Override
    public SaveHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.save_item,parent,false);
        SaveHolder holder=new SaveHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SaveHolder holder, int position) {
        Glide.with(context).load(files.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        int len = 0;
        try
        {
            len = files.size();
        }
        catch (Exception e)
        {
            Log.d("ex","exception");
        }
        return len;
    }


}
