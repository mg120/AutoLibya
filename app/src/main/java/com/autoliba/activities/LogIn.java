package com.autoliba.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.autoliba.R;
import com.autoliba.model.LoginModel;
import com.autoliba.model.UserSignUpModel;
import com.autoliba.networking.ApiClient;
import com.autoliba.networking.ApiService;
import com.fourhcode.forhutils.FUtilsValidation;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    EditText Name_ed, pass_ed;
    Button login_btn, new_sign_btn;
    SpotsDialog dialog;
    TextView forget_pass_txt;

    ApiService apiService;

    public static final String MY_PREFS_NAME = "all_user_data";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor user_data_edito;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Name_ed = findViewById(R.id.login_email_ed);
        pass_ed = findViewById(R.id.login_pass_ed);
        login_btn = findViewById(R.id.login_btn_id);
        new_sign_btn = findViewById(R.id.new_sign_btn_id);
        forget_pass_txt = findViewById(R.id.forget_pass_txt_id);

        login_btn.setOnClickListener(this);
        new_sign_btn.setOnClickListener(this);
        forget_pass_txt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.login_btn_id:

                // get token from Firebase
                String token = FirebaseInstanceId.getInstance().getToken();
                // Device ID
                String device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

                if (!FUtilsValidation.isEmpty(Name_ed, getString(R.string.required))
                        && !FUtilsValidation.isEmpty(pass_ed, getString(R.string.required))) {
                    dialog = new SpotsDialog(LogIn.this, getString(R.string.signup), R.style.Custom);
                    dialog.show();

                    apiService = ApiClient.getClient().create(ApiService.class);
                    Call<UserSignUpModel> call = apiService.login(Name_ed.getText().toString(), pass_ed.getText().toString(), token, device_id);
                    call.enqueue(new Callback<UserSignUpModel>() {
                        @Override
                        public void onResponse(Call<UserSignUpModel> call, Response<UserSignUpModel> response) {
                            dialog.dismiss();
                            UserSignUpModel loginModel = response.body();
                            boolean success = loginModel.getSuccess();
                            Log.e("success : ", success + "");
                            if (success) {
                                UserSignUpModel.Message message = loginModel.getMessage();
                                String userName = message.getUsername();

                                // Convert User Data to Gon OBJECT ...
                                Gson gson = new Gson();
                                String user_data_obj = gson.toJson(message);

                                // Save Data to Shared Preferneces ....
                                user_data_edito = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                user_data_edito.putString("userName", userName);
                                user_data_edito.putString("user_data", user_data_obj);
                                user_data_edito.commit();
                                user_data_edito.apply();

                                Intent intent = new Intent(LogIn.this, MainActivity.class);
                                intent.putExtra("user_data", message);
                                startActivity(intent);
                                Toasty.success(LogIn.this, getString(R.string.login_success), 1500).show();
                            } else {
                                String data = loginModel.getData();
                                Toasty.error(LogIn.this, getString(R.string.check_nameOrpass), 1500).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserSignUpModel> call, Throwable t) {
                            dialog.dismiss();
                            t.printStackTrace();
                        }
                    });
                }
                break;
            case R.id.new_sign_btn_id:
                Intent intent = new Intent(LogIn.this, LoginType.class);
                startActivity(intent);
                break;
            case R.id.forget_pass_txt_id:
                Intent forget_pass_intent = new Intent(LogIn.this, ForgetPassword.class);
                startActivity(forget_pass_intent);
                break;
        }
    }
}