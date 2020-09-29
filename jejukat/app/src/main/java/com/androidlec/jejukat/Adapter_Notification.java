package com.androidlec.jejukat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_Notification extends RecyclerView.Adapter<Adapter_Notification.mViewHolder> {

    private Context context;
    private ArrayList<Bean_Notification> mData;

    public Adapter_Notification(ArrayList<Bean_Notification> mData, Context context) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycle_notification_list, parent, false);
        return new mViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, final int position) {
        final String seq_notification = String.valueOf(mData.get(position).getSeq_notification());
        String title = mData.get(position).getTitle();
        String date = mData.get(position).getDate().substring(2,10);//yy-MM-dd형식으로 자름

        //setText
        holder.notification_seq_tv.setText(seq_notification);
        holder.notification_title_tv.setText(title);
        holder.notification_date_tv.setText(date);

        //클릭
        holder.layout_notificaion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                STATICDATA.SEQ_NOTIFICATION = seq_notification;
                Log.v("TAG", String.valueOf(STATICDATA.SEQ_NOTIFICATION));

                Intent intent = new Intent(view.getContext(), Activity_Notification_Selected.class);
                view.getContext().startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class mViewHolder extends RecyclerView.ViewHolder {
        TextView notification_seq_tv, notification_title_tv, notification_date_tv;
        LinearLayout layout_notificaion;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);
            notification_seq_tv = itemView.findViewById(R.id.tv_notification_seq);
            notification_title_tv = itemView.findViewById(R.id.tv_notification_title);
            notification_date_tv = itemView.findViewById(R.id.tv_notification_date);
            layout_notificaion = itemView.findViewById(R.id.Ll_notification);
        }
    }

}//------------
