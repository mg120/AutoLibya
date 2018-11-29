package com.autoliba.fragments;

import android.content.Context;
import android.net.Uri;
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
import com.autoliba.adapter.AutoNewsAdapter;
import com.autoliba.adapter.AutoShowsAdapter;
import com.autoliba.model.AutoNewsModel;
import com.autoliba.networking.ApiClient;
import com.autoliba.networking.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AutoNewsFrag extends Fragment {

    RecyclerView.LayoutManager layoutManager;
    RecyclerView autoNews_recycler;
    TextView no_available_autoNews;
    ProgressBar progressBar;
    AutoNewsAdapter adapter;

    ApiService apiService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("أخبار السيارات");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_auto_news, container, false);
        autoNews_recycler = view.findViewById(R.id.autoNews_recycler_id);
        progressBar = view.findViewById(R.id.autoNews_progress_id);
        no_available_autoNews = view.findViewById(R.id.autoNews_data_txt_id);
        layoutManager = new GridLayoutManager(getActivity(), 1);
        autoNewsData();
        return view;
    }

    private void autoNewsData() {

        apiService = ApiClient.getClient().create(ApiService.class);
        Call<AutoNewsModel> call = apiService.autoNews();
        call.enqueue(new Callback<AutoNewsModel>() {
            @Override
            public void onResponse(Call<AutoNewsModel> call, Response<AutoNewsModel> response) {
                AutoNewsModel autoNewsModel = response.body();
                boolean success = autoNewsModel.getSuccess();
                List<AutoNewsModel.Message> news_list = autoNewsModel.getMessage();
                if (news_list.size() > 0) {
                    progressBar.setVisibility(View.GONE);
                    no_available_autoNews.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    autoNews_recycler.setVisibility(View.VISIBLE);
                    autoNews_recycler.setLayoutManager(layoutManager);
                    autoNews_recycler.setHasFixedSize(true);
                    adapter = new AutoNewsAdapter(getActivity(), news_list);
                    autoNews_recycler.setAdapter(adapter);
                } else {
                    progressBar.setVisibility(View.GONE);
                    autoNews_recycler.setVisibility(View.GONE);
                    no_available_autoNews.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<AutoNewsModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
