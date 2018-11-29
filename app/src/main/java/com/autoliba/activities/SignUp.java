package com.autoliba.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.autoliba.R;
import com.autoliba.model.UserSignUpModel;
import com.autoliba.networking.ApiClient;
import com.autoliba.networking.ApiService;
import com.fourhcode.forhutils.FUtilsValidation;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    int type;
    EditText name_ed, email_ed, firstName_ed, lastName_ed, phone_ed, area_ed, country_ed, address_ed, pass_ed, confirmPass_ed;
    Button signUp_btn;
    SpotsDialog dialog;
    TextView add_photo_txt, photo_added_success;

    ApiService apiService;
    SharedPreferences.Editor user_data_edito;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    public static final String MY_PREFS_NAME = "all_user_data";
    private static final int INTENT_REQUEST_CODE = 110;
    MultipartBody.Part body = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if (getIntent().getExtras() != null) {
            type = getIntent().getExtras().getInt("type");
        }
        name_ed = findViewById(R.id.sign_name_ed);
        email_ed = findViewById(R.id.sign_email_ed);
        firstName_ed = findViewById(R.id.sign_firstName_ed);
        lastName_ed = findViewById(R.id.sign_lastName_ed);
        phone_ed = findViewById(R.id.sign_phoneNum_ed);
        area_ed = findViewById(R.id.sign_area_ed);
        country_ed = findViewById(R.id.sign_country_ed);
        address_ed = findViewById(R.id.sign_address_ed);
        pass_ed = findViewById(R.id.sign_pass_ed);
        confirmPass_ed = findViewById(R.id.sign_confirm_pass_ed);
        signUp_btn = findViewById(R.id.signIn_btn_id);
        add_photo_txt = findViewById(R.id.add_photo_txt_id);
        photo_added_success = findViewById(R.id.photo_added_success_txt);

        signUp_btn.setOnClickListener(this);
        add_photo_txt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.signIn_btn_id:

                // get token from Firebase
                String token = FirebaseInstanceId.getInstance().getToken();
                // Device ID
                String device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                if (!FUtilsValidation.isEmpty(name_ed, getString(R.string.required))
                        && !FUtilsValidation.isEmpty(email_ed, getString(R.string.required))
                        && FUtilsValidation.isValidEmail(email_ed, getString(R.string.enter_valid_email))
                        && !FUtilsValidation.isEmpty(firstName_ed, getString(R.string.required))
                        && !FUtilsValidation.isEmpty(lastName_ed, getString(R.string.required))
                        && !FUtilsValidation.isEmpty(phone_ed, getString(R.string.required))
                        && !FUtilsValidation.isEmpty(country_ed, getString(R.string.required))
                        && !FUtilsValidation.isEmpty(area_ed, getString(R.string.required))
                        && !FUtilsValidation.isEmpty(address_ed, getString(R.string.required))
                        && !FUtilsValidation.isEmpty(pass_ed, getString(R.string.required))
                        && !FUtilsValidation.isEmpty(confirmPass_ed, getString(R.string.required))
                        && FUtilsValidation.isPasswordEqual(pass_ed, confirmPass_ed, getString(R.string.pass_not_equal))
                        ) {

                    RequestBody NamePart = RequestBody.create(MultipartBody.FORM, name_ed.getText().toString().trim());

                    if (type == 1) {
                        dialog = new SpotsDialog(SignUp.this, getString(R.string.signup), R.style.Custom);
                        dialog.show();
                        apiService = ApiClient.getClient().create(ApiService.class);
                        Call<UserSignUpModel> call = apiService.userRegister(NamePart, firstName_ed.getText().toString().trim(), lastName_ed.getText().toString().trim(), email_ed.getText().toString().trim(), country_ed.getText().toString().trim(),
                                area_ed.getText().toString().trim(), phone_ed.getText().toString().trim(), address_ed.getText().toString().trim(), pass_ed.getText().toString().trim(), confirmPass_ed.getText().toString().trim(), token, device_id, body);
                        call.enqueue(new Callback<UserSignUpModel>() {
                            @Override
                            public void onResponse(Call<UserSignUpModel> call, Response<UserSignUpModel> response) {
                                dialog.dismiss();
                                UserSignUpModel userSignUpModel = response.body();
                                boolean success = userSignUpModel.getSuccess();
                                UserSignUpModel.Message message_data = userSignUpModel.getMessage();
                                Log.e("success: ", success + "");
                                Log.e("name: ", message_data.getUsername() + "");

                                // save data ...
                                user_data_edito = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(message_data);
                                user_data_edito.putString("user_data", json);
                                user_data_edito.commit();
                                user_data_edito.apply();

                                Intent intent = new Intent(SignUp.this, MainActivity.class);
                                intent.putExtra("user_data", message_data);
                                startActivity(intent);
                                // logoutPreferenceManager.writePreferences();
                                // open Main Activity ....
                                Toast.makeText(SignUp.this, "تم الدخول بنجاح", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<UserSignUpModel> call, Throwable t) {
                                dialog.dismiss();
                                t.printStackTrace();
                            }
                        });
                    } else if (type == 2) {
                        dialog = new SpotsDialog(SignUp.this, getString(R.string.signup), R.style.Custom);
                        dialog.show();
                        apiService = ApiClient.getClient().create(ApiService.class);
                        Call<UserSignUpModel> call = apiService.autoRegister(NamePart, firstName_ed.getText().toString().trim(), lastName_ed.getText().toString().trim(), email_ed.getText().toString().trim(), country_ed.getText().toString().trim(),
                                area_ed.getText().toString().trim(), phone_ed.getText().toString().trim(), address_ed.getText().toString().trim(), pass_ed.getText().toString().trim(), confirmPass_ed.getText().toString().trim(), token, device_id, body);
                        call.enqueue(new Callback<UserSignUpModel>() {
                            @Override
                            public void onResponse(Call<UserSignUpModel> call, Response<UserSignUpModel> response) {
                                dialog.dismiss();
                                UserSignUpModel userSignUpModel = response.body();
                                boolean success = userSignUpModel.getSuccess();
                                UserSignUpModel.Message message_data = userSignUpModel.getMessage();
                                Log.e("success: ", success + "");
                                Log.e("name: ", message_data.getUsername() + "");

                                if (success) {
                                    Toasty.success(SignUp.this, "تم التسجيل بنجاح, فى انتظار الموافقة من قبل المشرف", 1500).show();
                                }
                                Intent intent = new Intent(SignUp.this, LogIn.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<UserSignUpModel> call, Throwable t) {
                                dialog.dismiss();
                                t.printStackTrace();
                            }
                        });
                    }
                }
                break;
            case R.id.have_account_txt_id:
                startActivity(new Intent(SignUp.this, LogIn.class));
                break;
            case R.id.add_photo_txt_id:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                try {
                    startActivityForResult(intent, INTENT_REQUEST_CODE);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_REQUEST_CODE) {

            if (resultCode == RESULT_OK && data != null) {

                try {
                    android.net.Uri selectedImage = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
//                    profile_image.setImageBitmap(bitmap);
                    InputStream is = this.getContentResolver().openInputStream(data.getData());
                    add_photo_txt.setVisibility(View.GONE);
                    photo_added_success.setVisibility(View.VISIBLE);
                    createMultiPartFile(getBytes(is));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }
        return byteBuff.toByteArray();
    }

    private void createMultiPartFile(byte[] imageBytes) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
        body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
    }
}