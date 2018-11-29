package com.autoliba.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.autoliba.R;
import com.autoliba.adapter.AutoShowAdsAdapter;
import com.autoliba.adapter.AutoShowsAdapter;
import com.autoliba.adapter.HomeRecyclerAdapter;
import com.autoliba.model.HomeModel;
import com.autoliba.model.ProfileDataModel;
import com.autoliba.networking.ApiClient;
import com.autoliba.networking.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutoShowAds extends AppCompatActivity {

    RecyclerView autoAds_recycler;
    ProgressBar progressBar;
    TextView no_available_data, back;
    RecyclerView.LayoutManager layoutManager;
    ApiService apiService;
    AutoShowAdsAdapter adapter;

    int autoShow_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_show_ads);

        if (getIntent().getExtras() != null) {
            autoShow_id = getIntent().getExtras().getInt("autoShow_id");
        }
        back = findViewById(R.id.autoShow_ads_back_txt_id);
        autoAds_recycler = findViewById(R.id.AutoShow_recycler_id);
        progressBar = findViewById(R.id.AutoShow_progress_id);
        no_available_data = findViewById(R.id.no_available_data_txt_id);
        layoutManager = new GridLayoutManager(AutoShowAds.this, 2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ShowAdsData(autoShow_id + "");
    }

    private void ShowAdsData(String autoShow_id) {
        apiService = ApiClient.getClient().create(ApiService.class);
        Call<HomeModel> call = apiService.getAutoShowAds(autoShow_id);
        call.enqueue(new Callback<HomeModel>() {
            @Override
            public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {
                HomeModel homeModel = response.body();
                boolean success = homeModel.getSuccess();
                if (success) {
                    List<HomeModel.Message> list = homeModel.getMessage();
                    no_available_data.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    autoAds_recycler.setVisibility(View.VISIBLE);
                    autoAds_recycler.setLayoutManager(layoutManager);
                    autoAds_recycler.setHasFixedSize(true);
                    adapter = new AutoShowAdsAdapter(AutoShowAds.this, list);
                    autoAds_recycler.setAdapter(adapter);

                } else {
                    autoAds_recycler.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    no_available_data.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<HomeModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
