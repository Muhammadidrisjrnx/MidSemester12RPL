package com.example.rplrus021.midsemester12rpl;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.Holder> {
    private ArrayList<data> data;
    Context context;
    only_url url;
    database_helper database_helper;
//    MahasiswaHelper mahasiswaHelper;

    public adapter(Context context, ArrayList<data> data) {
        this.context = context;
        this.data = data;
//        mahasiswaHelper=new MahasiswaHelper(context);
        database_helper = new database_helper(context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new adapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        final data data2 = data.get(position);

        Glide.with(context)
                .load(data2.getGambar())
                .into(holder.imageView);
        holder.textView.setText(data2.getJudul());
        holder.button_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position1 = position;
                final String judul = data2.getJudul();
                final String description = data2.getDescription();
                final String gambar = data2.getGambar();
                Intent intent = new Intent(context.getApplicationContext(), description_movie.class);
                intent.putExtra("judul", judul);
                intent.putExtra("description", description);
                intent.putExtra("gambar", gambar);
                intent.putExtra("position", position1);
                context.startActivity(intent);

            }
        });
        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position2 = position;
                final String judul = data2.getJudul();
                database_helper.delete(judul);
                data.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,data.size());
//                int id=data2.getId();
//                mahasiswaHelper.open();
//                mahasiswaHelper.beginTransaction();
//                mahasiswaHelper.delete(id+"");
//                mahasiswaHelper.setTransactionSuccess();
//                mahasiswaHelper.endTransaction();
//                mahasiswaHelper.close();
//                data.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position,data.size());

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        TextView textView2;
        Button button_description;
        Button button_delete;

        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
            textView = (TextView) itemView.findViewById(R.id.textview_judul);
            textView2 = (TextView) itemView.findViewById(R.id.textview_tanggal);
            button_description = (Button) itemView.findViewById(R.id.btn_detail);
            button_delete = (Button) itemView.findViewById(R.id.btn_delete);
        }
    }

}
