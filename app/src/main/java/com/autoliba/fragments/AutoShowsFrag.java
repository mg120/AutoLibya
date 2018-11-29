package com.autoliba.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.autoliba.R;
import com.autoliba.adapter.AutoShowsAdapter;
import com.autoliba.adapter.HomeRecyclerAdapter;
import com.autoliba.model.AutoShowsModel;
import com.autoliba.model.HomeModel;
import com.autoliba.networking.ApiClient;
import com.autoliba.networking.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutoShowsFrag extends Fragment {

    private TextView no_available_data;
    private ProgressBar progressBar;
    private RecyclerView autoShows_reccyler;
    private RecyclerView.LayoutManager layoutManager;
    private AutoShowsAdapter adapter;

    ApiService apiService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("معارض السيارات");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_auto_shows, container, false);
        autoShows_reccyler = view.findViewById(R.id.autoShows_recycler_id);
        progressBar = view.findViewById(R.id.autoShows_progress_id);
        no_available_data = view.findViewById(R.id.autoshows_data_txt_id);
        layoutManager = new GridLayoutManager(getActivity(), 1);
        autoShowsData();
        return view;
    }

    private void autoShowsData() {
        apiService = ApiClient.getClient().create(ApiService.class);
        Call<AutoShowsModel> call = apiService.autoShows();
        call.enqueue(new Callback<AutoShowsModel>() {
            @Override
            public void onResponse(Call<AutoShowsModel> call, Response<AutoShowsModel> response) {
                AutoShowsModel autoShowsModel = response.body();
                boolean success = autoShowsModel.getSuccess();
                if (success) {
                    List<AutoShowsModel.Message> data_list = autoShowsModel.getMessage();
                    if (data_list.size() > 0) {
                        no_available_data.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        autoShows_reccyler.setVisibility(View.VISIBLE);
                        autoShows_reccyler.setLayoutManager(layoutManager);
                        autoShows_reccyler.setHasFixedSize(true);
                        adapter = new AutoShowsAdapter(getActivity(), data_list);
                        autoShows_reccyler.setAdapter(adapter);
                    } else {
                        autoShows_reccyler.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        no_available_data.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<AutoShowsModel> call, Throwable t) {

            }
        });
    }

}
