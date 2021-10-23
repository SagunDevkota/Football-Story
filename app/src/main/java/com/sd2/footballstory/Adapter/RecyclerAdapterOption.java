package com.sd2.footballstory.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sd2.footballstory.MainActivity;
import com.sd2.footballstory.R;
import com.sd2.footballstory.Resource.APIConnect;

import java.util.ArrayList;

public class RecyclerAdapterOption extends RecyclerView.Adapter<RecyclerAdapterOption.ViewHolder> {
    ArrayList<String> streamLinks;
    Context context;

    public RecyclerAdapterOption(ArrayList<String> arrayList, Context context) {
        this.streamLinks = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdapterOption.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_match,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterOption.ViewHolder holder, int position) {
        holder.matchName.setText("Link "+(position+1));
        holder.matchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                APIConnect apiConnect = new APIConnect(context);
//                String m3u8Link = apiConnect.m3u8Link(streamLinks.get(holder.getAdapterPosition()));
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("m3u8",streamLinks.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return streamLinks.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView matchName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            matchName = itemView.findViewById(R.id.match_name);

        }
    }
}
