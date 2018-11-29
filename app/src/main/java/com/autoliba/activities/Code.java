package com.autoliba.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.autoliba.NetworkTester;
import com.autoliba.R;
import com.autoliba.networking.ApiClient;
import com.autoliba.networking.ApiService;
import com.fourhcode.forhutils.FUtilsValidation;
import com.google.android.gms.common.api.ApiException;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Code extends AppCompatActivity implements View.OnClickListener {

    EditText code_ed;
    Button send_code_btn;
    String send_email;
    ApiService apiService;
    SpotsDialog dialog;

    NetworkTester networkTester = new NetworkTester(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        if (getIntent().getExtras() != null) {
            send_email = getIntent().getExtras().getString("email");
        }

        code_ed = findViewById(R.id.code_ed_id);
        send_code_btn = findViewById(R.id.send_btn_id);

        send_code_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.send_btn_id:

                if (!FUtilsValidation.isEmpty(code_ed, getString(R.string.required))) {
                    if (networkTester.isNetworkAvailable()) {
                        dialog = new SpotsDialog(Code.this, getString(R.string.sending), R.style.Custom);
                        dialog.show();

                        apiService = ApiClient.getClient().create(ApiService.class);
                        Call<SendCodeModel> call = apiService.send_Code(send_email, code_ed.getText().toString().trim());
                        call.enqueue(new Callback<SendCodeModel>() {
                            @Override
                            public void onResponse(Call<SendCodeModel> call, Response<SendCodeModel> response) {
                                SendCodeModel sendCodeModel = response.body();
                                if (sendCodeModel.getSuccess()) {
                                    Toasty.success(Code.this, "تم الارسال بنجاح", 1500).show();
                                    Intent intent = new Intent(Code.this, NewPass.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toasty.error(Code.this, "الكود الذى ادخلتة غير صحيح", 1500).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<SendCodeModel> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }
                }
                break;
        }
    }
}
