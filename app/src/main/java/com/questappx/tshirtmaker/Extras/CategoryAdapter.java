package com.questappx.tshirtmaker.Extras;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.questappx.tshirtmaker.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    String[] categoryNames;
    CategoryListener listener;
    int selected = 0;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyViewHolder(View view) {
            super(view);
            this.textView = view.findViewById(R.id.menuCategoryText);
        }
    }

    public CategoryAdapter(Context context, String[] categoryNames, CategoryListener listener) {
        this.categoryNames = categoryNames;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_category_item, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, @SuppressLint("RecyclerView") int i) {
        myViewHolder.textView.setText(categoryNames[i]);

        if(i == selected)
        {
            myViewHolder.textView.setBackgroundColor(ContextCompat.getColor(context, R.color.black));
            myViewHolder.textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        }
        else
        {
            myViewHolder.textView.setBackgroundColor(ContextCompat.getColor(context, R.color.grey2));
            myViewHolder.textView.setTextColor(ContextCompat.getColor(context, R.color.black));
        }

        myViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = i;
                listener.onItemSelected(i);
                notifyDataSetChanged();
            }
        });
    }

    public int getItemCount() {
        return this.categoryNames.length ;
    }

    public interface CategoryListener{
        public void onItemSelected(int position);
    }
}
