package com.androidlec.jejukat;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

public class Adapter_addplace_cat2 extends RecyclerView.Adapter<Adapter_addplace_cat2.ViewHolder> {

    private Context context;
    private ArrayList<Bean_Search> data = null;
    private RequestManager manager;

    public class ViewHolder extends RecyclerView.ViewHolder{
        Button button;
        Context context;

        ViewHolder(View itemView){
            super(itemView);

            button = itemView.findViewById(R.id.btn_cat2_search);
        }
    }

    public Adapter_addplace_cat2(ArrayList<Bean_Search> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public Adapter_addplace_cat2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recycle_search_cat2,parent,false);
        ViewHolder vh = new ViewHolder(view);
        context = parent.getContext();
        manager = Glide.with(context);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.button.setText(data.get(position).getCat2());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                STATICDATA.TEMP_CAT2 = data.get(position).getCat2();
                Toast.makeText(view.getContext(), STATICDATA.TEMP_CAT1 + " > " + STATICDATA.TEMP_CAT2+"을(를) 선택하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

