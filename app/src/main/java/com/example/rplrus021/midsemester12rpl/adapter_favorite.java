package com.example.rplrus021.midsemester12rpl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class adapter_favorite extends RecyclerView.Adapter<adapter_favorite.Holder> {
    ArrayList<data> arrayList;
    Context context;

    public adapter_favorite(Context context, ArrayList<data> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_favorite, parent, false);
        return new adapter_favorite.Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Glide.with(context)
                .load(arrayList.get(position).getGambar())
                .into(holder.image_view);
        holder.textView.setText(arrayList.get(position).getJudul());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView image_view;
        TextView textView;
        Button button;

        public Holder(View itemView) {
            super(itemView);
            image_view = (ImageView) itemView.findViewById(R.id.image_view);
            textView = (TextView) itemView.findViewById(R.id.textview_judul);
            button = (Button) itemView.findViewById(R.id.btn_details);
        }
    }
}
