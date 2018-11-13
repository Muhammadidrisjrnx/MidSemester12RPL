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
    database_favorite database_favorite;

    public adapter_favorite(Context context, ArrayList<data> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        database_favorite = new database_favorite(context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_favorite, parent, false);
        return new adapter_favorite.Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        final data data2 = arrayList.get(position);
        Glide.with(context)
                .load(arrayList.get(position).getGambar())
                .into(holder.image_view);
        holder.textView.setText(arrayList.get(position).getJudul());
        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String judul = data2.getJudul();
                database_favorite.delete(judul);
                arrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, arrayList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView image_view;
        TextView textView;
        Button button, button_delete;

        public Holder(View itemView) {
            super(itemView);
            image_view = (ImageView) itemView.findViewById(R.id.image_view);
            textView = (TextView) itemView.findViewById(R.id.textview_judul);
            button = (Button) itemView.findViewById(R.id.btn_details);
            button_delete = (Button) itemView.findViewById(R.id.btn_delete);
        }
    }
}
