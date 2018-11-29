package com.autoliba.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.autoliba.R;
import com.autoliba.model.LoginModel;
import com.autoliba.model.UserSignUpModel;
import com.autoliba.networking.ApiClient;
import com.autoliba.networking.ApiService;
import com.fourhcode.forhutils.FUtilsValidation;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPass extends AppCompatActivity implements View.OnClickListener {

    EditText new_pass, confirm_new_pass;
    Button send_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass);

        new_pass = findViewById(R.id.new_pass_ed);
        confirm_new_pass = findViewById(R.id.con_new_pass_ed);
        send_btn = findViewById(R.id.send_new_pass_btn);

        send_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.send_new_pass_btn:
                if (!FUtilsValidation.isEmpty(new_pass, getString(R.string.required))
                        && !FUtilsValidation.isEmpty(confirm_new_pass, getString(R.string.required))) {
                    ApiService apiService = ApiClient.getClient().create(ApiService.class);
                    Call<UserSignUpModel> call = apiService.new_Pass(MainActivity.user_data.getEmail(),
                            new_pass.getText().toString().trim(), confirm_new_pass.getText().toString().trim());
                    call.enqueue(new Callback<UserSignUpModel>() {
                        @Override
                        public void onResponse(Call<UserSignUpModel> call, Response<UserSignUpModel> response) {
                            UserSignUpModel loginModel = response.body();
                            boolean success = loginModel.getSuccess();
                            if (success) {
                                Toasty.success(NewPass.this, "تم تغيير كلمة المرور بنجاح", 1500).show();
                                UserSignUpModel.Message user_data = loginModel.getMessage();
                                Intent intent = new Intent(NewPass.this, MainActivity.class);
                                intent.putExtra("user_data", user_data);
                                startActivity(intent);
                                finish();

                            } else {
                                Toasty.error(NewPass.this, "كلمة المرور السابقة غير صحيحة!", 1500).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserSignUpModel> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }

                break;
        }

    }
}
