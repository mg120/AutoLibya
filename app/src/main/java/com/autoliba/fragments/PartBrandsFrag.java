package com.autoliba.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
import com.autoliba.adapter.PartBrandsAdapter;
import com.autoliba.model.PartBrandsModel;
import com.autoliba.networking.ApiClient;
import com.autoliba.networking.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PartBrandsFrag extends Fragment {

    RecyclerView.LayoutManager layoutManager;
    RecyclerView partBrands_recycler;
    TextView no_available_partBrands;
    ProgressBar progressBar;
    PartBrandsAdapter adapter;

    ApiService apiService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("قطع الغيار");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_part_brands, container, false);
        partBrands_recycler = view.findViewById(R.id.partBrands_recycler_id);
        progressBar = view.findViewById(R.id.partBrands_progress_id);
        no_available_partBrands = view.findViewById(R.id.partBrands_data_txt_id);
        layoutManager = new GridLayoutManager(getActivity(), 1);
        partBrandsData();
        return view;
    }

    private void partBrandsData() {
        progressBar.setVisibility(View.GONE);
        apiService = ApiClient.getClient().create(ApiService.class);
        Call<PartBrandsModel> call = apiService.partBrands();
        call.enqueue(new Callback<PartBrandsModel>() {
            @Override
            public void onResponse(Call<PartBrandsModel> call, Response<PartBrandsModel> response) {
                PartBrandsModel partBrandsModel = response.body();
                List<PartBrandsModel.Part> partList = partBrandsModel.getMessage().getParts();

                if (partList.size() > 0) {
                    no_available_partBrands.setVisibility(View.GONE);
                    partBrands_recycler.setVisibility(View.VISIBLE);
                    partBrands_recycler.setLayoutManager(layoutManager);
                    partBrands_recycler.setHasFixedSize(true);
                    adapter = new PartBrandsAdapter(getActivity(), partList);
                    partBrands_recycler.setAdapter(adapter);
                } else {
                    partBrands_recycler.setVisibility(View.GONE);
                    no_available_partBrands.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<PartBrandsModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
