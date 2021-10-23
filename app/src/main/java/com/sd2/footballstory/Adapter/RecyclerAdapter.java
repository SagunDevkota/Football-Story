package com.sd2.footballstory.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sd2.footballstory.LinkOption;
import com.sd2.footballstory.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    ArrayList<String> arrayList;
    Context context;
    Intent intent;

    public RecyclerAdapter(ArrayList<String> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_match,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        String match = arrayList.get(position);
        holder.matchName.setText(match);
        holder.cardView.setOnClickListener(v -> {
            intent = new Intent(v.getContext(), LinkOption.class);
            intent.putExtra("match_name",match);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView matchName;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            matchName = itemView.findViewById(R.id.match_name);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }
}
