package com.autoliba.fragments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.autoliba.NetworkTester;
import com.autoliba.R;
import com.autoliba.activities.MainActivity;
import com.autoliba.adapter.AdsAdapter;
import com.autoliba.model.ProfileDataModel;
import com.autoliba.model.UserSignUpModel;
import com.autoliba.networking.ApiClient;
import com.autoliba.networking.ApiService;
import com.fourhcode.forhutils.FUtilsValidation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    TextView add_photo_txt, photo_added_txt;
    EditText email_ed, firstName_ed, lastName_ed, phone_ed, area_ed, country_ed, address_ed;
    Button update_profile_btn;
    SpotsDialog dialog;
    public static TextView no_available_ads_txt;
    public static RecyclerView ads_recyclerView;

    ApiService apiService;
    private static final int INTENT_REQUEST_CODE = 12345;
    MultipartBody.Part body;
    AdsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("الملف الشخصى");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        add_photo_txt = rootView.findViewById(R.id.update_profile_img_txt_id);
        photo_added_txt = rootView.findViewById(R.id.profile_photo_added_success_txt);
        email_ed = rootView.findViewById(R.id.profile_email_ed);
        firstName_ed = rootView.findViewById(R.id.profile_firstName_ed);
        lastName_ed = rootView.findViewById(R.id.profile_lastName_ed);
        phone_ed = rootView.findViewById(R.id.profile_phoneNum_ed);
        area_ed = rootView.findViewById(R.id.profile_area_ed);
        country_ed = rootView.findViewById(R.id.profile_country_ed);
        address_ed = rootView.findViewById(R.id.profile_address_ed);
        update_profile_btn = rootView.findViewById(R.id.update_profile_btn_id);
        no_available_ads_txt = rootView.findViewById(R.id.no_available_ads_txt_id);
        ads_recyclerView = rootView.findViewById(R.id.ads_recycler_id);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set Data to Views
        email_ed.setText(MainActivity.user_data.getEmail());
        firstName_ed.setText(MainActivity.user_data.getFirstname());
        lastName_ed.setText(MainActivity.user_data.getLastname());
        phone_ed.setText(MainActivity.user_data.getPhone());
        area_ed.setText(MainActivity.user_data.getArea());
        country_ed.setText(MainActivity.user_data.getCountry());
        address_ed.setText(MainActivity.user_data.getAddress());

        getProfileData(MainActivity.user_data.getId());

        add_photo_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                try {
                    startActivityForResult(intent, INTENT_REQUEST_CODE);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        update_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!FUtilsValidation.isEmpty(email_ed, getString(R.string.required))
                        && FUtilsValidation.isValidEmail(email_ed, getString(R.string.enter_valid_email))
                        && !FUtilsValidation.isEmpty(firstName_ed, getString(R.string.required))
                        && !FUtilsValidation.isEmpty(lastName_ed, getString(R.string.required))
                        && !FUtilsValidation.isEmpty(phone_ed, getString(R.string.required))
                        && !FUtilsValidation.isEmpty(country_ed, getString(R.string.required))
                        && !FUtilsValidation.isEmpty(area_ed, getString(R.string.required))
                        && !FUtilsValidation.isEmpty(address_ed, getString(R.string.required))
                        ) {
                    if (isNetworkAvailable()) {

                        RequestBody id_Part = RequestBody.create(MultipartBody.FORM, MainActivity.user_data.getId() + "");

                        dialog = new SpotsDialog(getActivity(), getString(R.string.updatting), R.style.Custom);
                        dialog.show();
                        apiService = ApiClient.getClient().create(ApiService.class);
                        Call<UserSignUpModel> call = apiService.update_profile(id_Part, firstName_ed.getText().toString().trim(), lastName_ed.getText().toString().trim(), email_ed.getText().toString().trim(),
                                phone_ed.getText().toString().trim(), area_ed.getText().toString().trim(), country_ed.getText().toString(), address_ed.getText().toString().trim(), body);

                        call.enqueue(new Callback<UserSignUpModel>() {
                            @Override
                            public void onResponse(Call<UserSignUpModel> call, Response<UserSignUpModel> response) {
                                dialog.dismiss();
                                UserSignUpModel userSignUpModel = response.body();
                                boolean success = userSignUpModel.getSuccess();
                                if (success) {

                                    Toasty.success(getActivity(), "تم تحديث بياناتك بنجاح", 1500).show();
                                    UserSignUpModel.Message user_data = userSignUpModel.getMessage();
                                    //INTENT OBJ
                                    Intent i = new Intent(getActivity().getBaseContext(), MainActivity.class);
                                    //PACK DATA
                                    i.putExtra("user_data", user_data);
                                    //START ACTIVITY
                                    getActivity().startActivity(i);

                                } else {
                                    Toasty.error(getActivity(), "حدث خطأ ما, حاول مرة اخرى", 1500).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UserSignUpModel> call, Throwable t) {
                                dialog.dismiss();
                                t.printStackTrace();
                            }
                        });
                    } else {
                        Toasty.error(getActivity(), getString(R.string.error_connection), 1500).show();
                    }
                }
            }
        });

    }

    private void getProfileData(Integer id) {
        apiService = ApiClient.getClient().create(ApiService.class);
        Call<ProfileDataModel> call = apiService.getProfileData(id + "");
        call.enqueue(new Callback<ProfileDataModel>() {
            @Override
            public void onResponse(Call<ProfileDataModel> call, Response<ProfileDataModel> response) {
                ProfileDataModel profileDataModel = response.body();
                boolean success = profileDataModel.getSuccess();
                if (success) {
                    ProfileDataModel.Message message_data = profileDataModel.getMessage();
                    List<ProfileDataModel.Myad> myads_list = profileDataModel.getMessage().getMyads();

                    if (myads_list.size() > 0) {
                        no_available_ads_txt.setVisibility(View.GONE);
                        ads_recyclerView.setVisibility(View.VISIBLE);
                        ads_recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                        ads_recyclerView.setHasFixedSize(true);
                        adapter = new AdsAdapter(getActivity(), myads_list);
                        ads_recyclerView.setAdapter(adapter);

                    }
                } else {
                    ads_recyclerView.setVisibility(View.GONE);
                    no_available_ads_txt.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ProfileDataModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {

            try {
                android.net.Uri selectedImage = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
//                    profile_image.setImageBitmap(bitmap);
                InputStream is = getActivity().getContentResolver().openInputStream(data.getData());
                add_photo_txt.setVisibility(View.GONE);
                photo_added_txt.setVisibility(View.VISIBLE);
                createMultiPartFile(getBytes(is));

            } catch (IOException e) {
                e.printStackTrace();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();
        /// if no network is available networkInfo will be null
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
