package com.androidlec.jejukat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

public class Adapter_Main_cat extends RecyclerView.Adapter<Adapter_Main_cat.ViewHolder> {

    private Context context;
    private ArrayList<Bean_Main> data = null;
    private RequestManager manager;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, description;
        ImageView img;
        LinearLayout textlayout,layout;

        ViewHolder(View itemView){
            super(itemView);

            layout = itemView.findViewById(R.id.layout_recycle);
            textlayout = itemView.findViewById(R.id.layout_recycle_text);
            title = itemView.findViewById(R.id.text_recycle_main_cat);
            description = itemView.findViewById(R.id.text_recycle_main_des);
            img = itemView.findViewById(R.id.img_recycle_main_cat);
        }
    }

    public Adapter_Main_cat(ArrayList<Bean_Main> data) {
        this.data = data;
    }

    @Override
    public Adapter_Main_cat.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recycle_main_cat,parent,false);
        ViewHolder vh = new ViewHolder(view);
        context = parent.getContext();
        manager = Glide.with(context);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.textlayout.setBackgroundColor(Color.parseColor(data.get(position).getColor()));
        holder.title.setText(data.get(position).getTitle());
        holder.description.setText(data.get(position).getDescription());
        holder.img.setBackgroundResource(data.get(position).getImage());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Activity_Search.class);
                STATICDATA.CAT1 = data.get(position).getTitle();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
