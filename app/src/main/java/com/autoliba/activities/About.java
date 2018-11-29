package com.autoliba.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.autoliba.R;
import com.autoliba.model.SettingInfoModel;
import com.autoliba.networking.ApiClient;
import com.autoliba.networking.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class About extends AppCompatActivity {

    TextView about_txt, about_back;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        about_back = findViewById(R.id.about_back_txt_id);
        about_txt = findViewById(R.id.about_txtV_id);
        apiService = ApiClient.getClient().create(ApiService.class);
        Call<SettingInfoModel> call = apiService.getSettingInfo();
        call.enqueue(new Callback<SettingInfoModel>() {
            @Override
            public void onResponse(Call<SettingInfoModel> call, Response<SettingInfoModel> response) {
                SettingInfoModel settingInfoModel = response.body();
                String about_us = settingInfoModel.getMessage().getSetting().get(0).getAboutus();
                about_txt.setText(Html.fromHtml(about_us));
            }

            @Override
            public void onFailure(Call<SettingInfoModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

        about_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
