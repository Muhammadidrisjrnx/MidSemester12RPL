package com.example.rplrus021.midsemester12rpl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class now_playing extends Fragment {
    public ArrayList<Result> data;
    RecyclerView recyclerView;
    database_helper database;
    View rootView;
    ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_now_playing, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycle_view);
        database = new database_helper(rootView.getContext());
        shimmerFrameLayout = (ShimmerFrameLayout) rootView.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmerAnimation();
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            load_data_from_json();
        } else {
            connected = false;

        }
        return rootView;
    }

    private void load_data_from_json() {
        json_api service = retrofitclientinstance.getRetrofitInstance().create(json_api.class);
        Call<jsonRespond> jsonNowPlaying = service.getJsonNowPlaying();
        jsonNowPlaying.enqueue(new Callback<jsonRespond>() {
            @Override
            public void onResponse(Call<jsonRespond> call, Response<jsonRespond> response) {
                jsonRespond jsonRespond = response.body();
                data = new ArrayList<>(Arrays.asList(response.body().getResults()));
                adapter adapter = new adapter(rootView.getContext(), data);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rootView.getContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<jsonRespond> call, Throwable t) {

            }
        });
    }
}
