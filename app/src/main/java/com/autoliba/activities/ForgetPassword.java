package com.autoliba.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.autoliba.NetworkTester;
import com.autoliba.R;
import com.autoliba.model.ForgetPassSendMailModel;
import com.autoliba.networking.ApiClient;
import com.autoliba.networking.ApiService;
import com.fourhcode.forhutils.FUtilsValidation;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword extends AppCompatActivity implements View.OnClickListener {

    EditText email_ed;
    Button send_btn;
    SpotsDialog dialog;

    ApiService apiService;
    NetworkTester networkTester = new NetworkTester(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        email_ed = findViewById(R.id.pass_email_ed);
        send_btn = findViewById(R.id.send_btn_id);

        send_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final String email = email_ed.getText().toString().trim();

        if (!FUtilsValidation.isEmpty(email_ed, getString(R.string.required))) {

            if (networkTester.isNetworkAvailable()) {
                dialog = new SpotsDialog(ForgetPassword.this, getString(R.string.sending), R.style.Custom);
                dialog.show();

                apiService = ApiClient.getClient().create(ApiService.class);
                Call<ForgetPassSendMailModel> call = apiService.forgetPassword_mail(email);
                call.enqueue(new Callback<ForgetPassSendMailModel>() {
                    @Override
                    public void onResponse(Call<ForgetPassSendMailModel> call, Response<ForgetPassSendMailModel> response) {
                        dialog.dismiss();
                        ForgetPassSendMailModel forgetPassSendMailModel = response.body();
                        if (forgetPassSendMailModel.getSuccess()) {
                            Toasty.success(ForgetPassword.this, "تم ارسال الكود الى البريد الخاص بك", 1500).show();
                            Intent intent = new Intent(ForgetPassword.this, Code.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                        } else {
                            Toasty.error(ForgetPassword.this, "هذا البريد غير مسجل مسبقا, برجاء التحقق من البريد!", 1500).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ForgetPassSendMailModel> call, Throwable t) {
                        dialog.dismiss();
                        t.printStackTrace();
                    }
                });
            }
        }
    }
}
