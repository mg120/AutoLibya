package com.autoliba.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.autoliba.NetworkTester;
import com.autoliba.R;
import com.autoliba.model.ChangePassModel;
import com.autoliba.networking.ApiClient;
import com.autoliba.networking.ApiService;
import com.fourhcode.forhutils.FUtilsValidation;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePass extends AppCompatActivity implements View.OnClickListener {

    EditText old_pass_ed, new_pass_ed, confirm_pass_ed;
    Button change_btn;
    SpotsDialog dialog;
    TextView back;

    NetworkTester networkTester = new NetworkTester(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        back = findViewById(R.id.changePass_back_txt_id);
        old_pass_ed = findViewById(R.id.change_old_pass_ed);
        new_pass_ed = findViewById(R.id.change_new_pass_ed);
        old_pass_ed = findViewById(R.id.change_con_new_pass_ed);
        change_btn = findViewById(R.id.change_pass_btn_id);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        change_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (networkTester.isNetworkAvailable()) {

            if (!FUtilsValidation.isEmpty(old_pass_ed, getString(R.string.required))
                    && !FUtilsValidation.isEmpty(old_pass_ed, getString(R.string.required))
                    && !FUtilsValidation.isEmpty(confirm_pass_ed, getString(R.string.required))) {
                dialog = new SpotsDialog(ChangePass.this, getString(R.string.changing), R.style.Custom);
                dialog.show();

                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                Call<ChangePassModel> call = apiService.change_Pass(MainActivity.user_data.getId() + "", old_pass_ed.getText().toString().trim(),
                        new_pass_ed.getText().toString().trim(), confirm_pass_ed.getText().toString().trim());
                call.enqueue(new Callback<ChangePassModel>() {
                    @Override
                    public void onResponse(Call<ChangePassModel> call, Response<ChangePassModel> response) {
                        dialog.dismiss();
                        ChangePassModel changePassModel = response.body();
                        boolean success = changePassModel.getSuccess();
                        if (success) {
                            Toasty.success(ChangePass.this, "تم تغيير كلمة المرور بنجاح!", 1500).show();
                            finish();
                        } else {
                            Toasty.success(ChangePass.this, "كلمة المرور السابقة غير صحيحة", 1500).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ChangePassModel> call, Throwable t) {
                        dialog.dismiss();
                        t.printStackTrace();
                    }
                });
            }
        }
    }
}
