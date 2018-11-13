package com.example.rplrus021.midsemester12rpl;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class upcoming extends Fragment {
    View root;
    RecyclerView recyclerView;
    public ArrayList<data> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_upcoming, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recycle_view);
        load_data_upcoming();
        return root;
    }

    private void load_data_upcoming() {
        json_api service = retrofitclientinstance.getRetrofitInstance().create(json_api.class);
        Call<jsonRespond> call = service.getJsonUpcoming();
        call.enqueue(new Callback<jsonRespond>() {
            @Override
            public void onResponse(Call<jsonRespond> call, Response<jsonRespond> response) {
                jsonRespond jsonRespond = response.body();
                data = new ArrayList<>(Arrays.asList(jsonRespond.getResults()));
                Log.d("responku", "onResponse: "+jsonRespond.getResults());
                adapter adapter = new adapter(root.getContext(), data);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<jsonRespond> call, Throwable t) {
                Log.d("responku", "onFailure: gagal");
            }
        });
    }
}
