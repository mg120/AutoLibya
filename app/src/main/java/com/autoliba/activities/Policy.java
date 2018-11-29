package com.autoliba.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.style.TtsSpan;
import android.view.View;
import android.widget.TextView;

import com.autoliba.R;
import com.autoliba.model.SettingInfoModel;
import com.autoliba.networking.ApiClient;
import com.autoliba.networking.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Policy extends AppCompatActivity {

    TextView policy_txt, policy_back;

    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);

        policy_back = findViewById(R.id.hpolicy_back_txt_id);
        policy_txt = findViewById(R.id.policy_txtV_id);

        apiService = ApiClient.getClient().create(ApiService.class);
        Call<SettingInfoModel> call = apiService.getSettingInfo();
        call.enqueue(new Callback<SettingInfoModel>() {
            @Override
            public void onResponse(Call<SettingInfoModel> call, Response<SettingInfoModel> response) {
                SettingInfoModel settingInfoModel = response.body();
                List<SettingInfoModel.Setting> list = settingInfoModel.getMessage().getSetting();
                String policy = list.get(0).getPolicy();
                policy_txt.setText(Html.fromHtml(policy));
            }

            @Override
            public void onFailure(Call<SettingInfoModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

        policy_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
